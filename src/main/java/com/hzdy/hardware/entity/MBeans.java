package com.hzdy.hardware.entity;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.management.Attribute;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import com.hzdy.hardware.entity.MBeanNode.MBeanAttribute;

public final class MBeans {
	// 属性分隔符
	static final char ATTRIBUTES_SEPARATOR = '|';

	private static final String JAVA_LANG_MBEAN_DESCRIPTION = "Information on the management interface of the MBean";
	// 通过MBeanNode的名字来进行比较
	private static final Comparator<MBeanNode> NODE_COMPARATOR = new Comparator<MBeanNode>() {
		@Override
		public int compare(MBeanNode o1, MBeanNode o2) {
			return o1.getName() != null ? o1.getName().compareTo(o2.getName()) : 0;
		}
	};
	// 通过MBeanAttribute的名字进行比较
	private static final Comparator<MBeanAttribute> ATTRIBUTE_COMPARATOR = new Comparator<MBeanAttribute>() {
		@Override
		public int compare(MBeanAttribute o1, MBeanAttribute o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	// MBeanServer是一个包含所有注册MBean的仓库.它是JMX代理层的核心,每一个MBean具有一个唯一标志,叫ObjectName.
	private final MBeanServer mbeanServer;

	MBeans() {
		this(getPlatformMBeanServer());
	}

	/**
	 * @param mbeanServer
	 *            提供一个MBeanServer
	 */
	private MBeans(MBeanServer mbeanServer) {
		super();
		this.mbeanServer = mbeanServer;
	}
    //获取线程池
	Set<ObjectName> getTomcatThreadPools() throws MalformedObjectNameException {
		return mbeanServer.queryNames(new ObjectName("*:type=ThreadPool,*"), null);
	}
	//获取可用的全局需要处理器数量
	Set<ObjectName> getTomcatGlobalRequestProcessors() throws MalformedObjectNameException {
		return mbeanServer.queryNames(new ObjectName("*:type=GlobalRequestProcessor,*"), null);
	}
	//获取NioBufferPools
	Set<ObjectName> getNioBufferPools() throws MalformedObjectNameException {
		return mbeanServer.queryNames(new ObjectName("java.nio:type=BufferPool,*"), null);
	}
	//mbeanServer是用来管理ObjectName,即通过ObjectName获取属性
	Object getAttribute(ObjectName name, String attribute) throws JMException {
		return mbeanServer.getAttribute(name, attribute);
	}
	//获得所有的MBeanNode
	static List<MBeanNode> getAllMBeanNodes() throws JMException {
		//Oracle JRockit (原来的 Bea JRockit)系列产品是一个全面的Java运行时解决方案组合,包括了行业最快的标准Java解决方案
		initJRockitMBeansIfNeeded();
		/*
		 * List<MBeanNode><-  MBeanNode <-    MBeans   <-    MBeanNode
		 *      result    <-platformNode<-platformMBeans<-getMBeanNodes()
		 */
		//创建MBean节点集合
		final List<MBeanNode> result = new ArrayList<MBeanNode>();
		//获取当前平台的MBeanServer
		final MBeanServer platformMBeanServer = getPlatformMBeanServer();
		//创建平台节点
		final MBeanNode platformNode = new MBeanNode("");
		// MBeans pour la plateforme( MBeans的平台)
		final MBeans platformMBeans = new MBeans();
		//给平台节点的孩子添加所有平台节点
		platformNode.getChildren().addAll(platformMBeans.getMBeanNodes());
		//给MBean节点添加
		result.add(platformNode);

		// pour JBoss 5.0.x, les MBeans de JBoss sont dans un autre MBeanServer
		//对所有的MBeanServers进行判断
		for (final MBeanServer mbeanServer : getMBeanServers()) {
			//如果mbeanServer不等于当前的MBeanServer
			if (!mbeanServer.equals(platformMBeanServer)) {
				//getDefaultDomain:返回用于命名MBean的默认域。
				final MBeanNode node = new MBeanNode(mbeanServer.getDefaultDomain());
				final MBeans mbeans = new MBeans(mbeanServer);
				node.getChildren().addAll(mbeans.getMBeanNodes());
				result.add(node);
			}
		}
		return result;
	}
	//Oracle JRockit (原来的 Bea JRockit)系列产品是一个全面的Java运行时解决方案组合,包括了行业最快的标准Java解决方案
	private static void initJRockitMBeansIfNeeded() {
		if (System.getProperty("java.vendor").contains("BEA")) {
			try {
				// initialisation des MBeans jrockit comme indiqué dans
				// http://blogs.oracle.com/hirt/jrockit/
				try {
					getPlatformMBeanServer().getMBeanInfo(new ObjectName("bea.jrockit.management:type=JRockitConsole"));
				} catch (final InstanceNotFoundException e1) {
					getPlatformMBeanServer().createMBean("bea.jrockit.management.JRockitConsole", null);
					System.out.println("JRockit MBeans initialized");
				}
			} catch (final JMException e) {
				throw new IllegalStateException(e);
			}
		}
	}
	//获取MBeanNode集合
	private List<MBeanNode> getMBeanNodes() throws JMException {
		final List<MBeanNode> result = new ArrayList<MBeanNode>();
		final Set<ObjectName> names = mbeanServer.queryNames(null, null);
		for (final ObjectName name : names) {
			final String domain = name.getDomain();
			if ("jboss.deployment".equals(domain)) {
				// la partie "jboss.deployment" dans JBoss (5.0.x) est plutôt
				// inutile et trop lourde
				continue;
			}
			MBeanNode domainNode = getMBeanNodeFromList(result, domain);
			if (domainNode == null) {
				domainNode = new MBeanNode(domain);
				result.add(domainNode);
			}
			final String keyPropertyListString = name.getKeyPropertyListString();
			final String firstPropertyValue;
			final int indexOf = keyPropertyListString.indexOf('=');
			if (indexOf == -1) {
				// n'arrive probablement pas, mais au cas où
				firstPropertyValue = null;
			} else {
				firstPropertyValue = name.getKeyProperty(keyPropertyListString.substring(0, indexOf));
			}
			MBeanNode firstPropertyNode = getMBeanNodeFromList(domainNode.getChildren(), firstPropertyValue);
			if (firstPropertyNode == null) {
				firstPropertyNode = new MBeanNode(firstPropertyValue);
				domainNode.getChildren().add(firstPropertyNode);
			}
			final MBeanNode mbean = getMBeanNode(name);
			firstPropertyNode.getChildren().add(mbean);
		}
		sortMBeanNodes(result);
		return result;
	}

	private void sortMBeanNodes(List<MBeanNode> nodes) {
		if (nodes.size() > 1) {
			Collections.sort(nodes, NODE_COMPARATOR);
		}

		for (final MBeanNode node : nodes) {
			final List<MBeanNode> children = node.getChildren();
			if (children != null) {
				sortMBeanNodes(children);
			}
			final List<MBeanAttribute> attributes = node.getAttributes();
			if (attributes != null && attributes.size() > 1) {
				Collections.sort(attributes, ATTRIBUTE_COMPARATOR);
			}
		}
	}

	private static MBeanNode getMBeanNodeFromList(List<MBeanNode> list, String name) {
		for (final MBeanNode node : list) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	private MBeanNode getMBeanNode(ObjectName name) throws JMException {
		final String mbeanName = name.toString();
		final MBeanInfo mbeanInfo = mbeanServer.getMBeanInfo(name);
		final String description = formatDescription(mbeanInfo.getDescription());
		final MBeanAttributeInfo[] attributeInfos = mbeanInfo.getAttributes();
		final List<MBeanAttribute> attributes = getAttributes(name, attributeInfos);
		// les attributs seront triés par ordre alphabétique dans getMBeanNodes
		return new MBeanNode(mbeanName, description, attributes);
	}

	private List<MBeanAttribute> getAttributes(ObjectName name, MBeanAttributeInfo[] attributeInfos) {
		final List<String> attributeNames = new ArrayList<String>(attributeInfos.length);
		for (final MBeanAttributeInfo attribute : attributeInfos) {
			// on ne veut pas afficher l'attribut password, jamais
			// (notamment, dans users tomcat ou dans datasources tomcat)
			if (attribute.isReadable() && !"password".equalsIgnoreCase(attribute.getName())) {
				attributeNames.add(attribute.getName());
			}
		}
		final String[] attributeNamesArray = attributeNames.toArray(new String[attributeNames.size()]);
		final List<MBeanAttribute> result = new ArrayList<MBeanAttribute>();
		try {
			// issue 116: asList sur mbeanServer.getAttributes(name,
			// attributeNamesArray) n'existe qu'en java 1.6
			final List<Object> attributes = mbeanServer.getAttributes(name, attributeNamesArray);
			for (final Object object : attributes) {
				final Attribute attribute = (Attribute) object;
				final Object value = convertValueIfNeeded(attribute.getValue());
				final String attributeDescription = getAttributeDescription(attribute.getName(), attributeInfos);
				final String formattedAttributeValue = formatAttributeValue(value);
				final MBeanAttribute mbeanAttribute = new MBeanAttribute(attribute.getName(), attributeDescription,
						formattedAttributeValue);
				result.add(mbeanAttribute);
			}
		} catch (final Exception e) {
			// issue 201: do not stop to render MBeans tree when exception in
			// mbeanServer.getAttributes
			final MBeanAttribute mbeanAttribute = new MBeanAttribute("exception", null, e.toString());
			result.add(mbeanAttribute);
		}
		return result;
	}

	private String formatAttributeValue(Object attributeValue) {
		try {
			if (attributeValue instanceof List) {
				final StringBuilder sb = new StringBuilder();
				sb.append('[');
				boolean first = true;
				for (final Object value : (List<?>) attributeValue) {
					if (first) {
						first = false;
					} else {
						sb.append(",\n");
					}
					sb.append(String.valueOf(value));
				}
				sb.append(']');
				return sb.toString();
			}
			return String.valueOf(attributeValue);
		} catch (final Exception e) {
			return e.toString();
		}
	}

	private String formatDescription(String description) {
		// les descriptions des MBeans de java.lang n'apportent aucune
		// information utile
		if (description == null || JAVA_LANG_MBEAN_DESCRIPTION.equals(description)) {
			return null;
		}
		int indexOf = description.indexOf("  ");
		if (indexOf != -1) {
			// certaines descriptions de MBeans ou d'attributs dans Tomcat 6 et
			// 7 contiennent de nombreux espaces qui se suivent
			final StringBuilder sb = new StringBuilder(description);
			while (indexOf != -1) {
				sb.deleteCharAt(indexOf);
				indexOf = sb.indexOf("  ");
			}
			return sb.toString();
		}
		return description;
	}

	private Object convertValueIfNeeded(Object value) {
		if (value instanceof CompositeData) {
			final CompositeData data = (CompositeData) value;
			final Map<String, Object> values = new TreeMap<String, Object>();
			for (final String key : data.getCompositeType().keySet()) {
				values.put(key, convertValueIfNeeded(data.get(key)));
			}
			return values;
		} else if (value instanceof CompositeData[]) {
			final List<Object> list = new ArrayList<Object>();
			for (final CompositeData data : (CompositeData[]) value) {
				list.add(convertValueIfNeeded(data));
			}
			return list;
		} else if (value instanceof Object[]) {
			return Arrays.asList((Object[]) value);
		} else if (value instanceof TabularData) {
			final TabularData tabularData = (TabularData) value;
			return convertValueIfNeeded(tabularData.values());
		} else if (value instanceof Collection) {
			final List<Object> list = new ArrayList<Object>();
			for (final Object data : (Collection<?>) value) {
				list.add(convertValueIfNeeded(data));
			}
			return list;
		}
		return convertJRockitValueIfNeeded(value);
	}

	private static Object convertJRockitValueIfNeeded(Object value) {
		if (value instanceof double[]) {
			// pour jrockit MBeans
			final List<Double> list = new ArrayList<Double>();
			for (final double data : (double[]) value) {
				list.add(data);
			}
			return list;
		} else if (value instanceof int[]) {
			// pour jrockit MBeans
			final List<Integer> list = new ArrayList<Integer>();
			for (final int data : (int[]) value) {
				list.add(data);
			}
			return list;
		}
		return value;
	}

	private static List<Object> getConvertedAttributes(List<String> mbeanAttributes) {
		initJRockitMBeansIfNeeded();

		final List<Object> result = new ArrayList<Object>();
		final List<MBeanServer> mBeanServers = getMBeanServers();
		for (final String mbeansAttribute : mbeanAttributes) {
			final int lastIndexOfPoint = mbeansAttribute.lastIndexOf('.');
			if (lastIndexOfPoint <= 0) {
				throw new IllegalArgumentException(mbeansAttribute);
			}
			final String name = mbeansAttribute.substring(0, lastIndexOfPoint);
			final String attribute = mbeansAttribute.substring(lastIndexOfPoint + 1);
			// on ne veut pas afficher l'attribut password, jamais
			// (notamment, dans users tomcat ou dans datasources tomcat)
			if ("password".equalsIgnoreCase(attribute)) {
				throw new IllegalArgumentException(name + '.' + attribute);
			}
			InstanceNotFoundException instanceNotFoundException = null;
			for (final MBeanServer mbeanServer : mBeanServers) {
				try {
					final MBeans mbeans = new MBeans(mbeanServer);
					final Object jmxValue = mbeans
							.convertValueIfNeeded(mbeans.getAttribute(new ObjectName(name), attribute));
					result.add(jmxValue);
					instanceNotFoundException = null;
					// ObjectName trouvé dans ce MBeanServer, inutile de
					// chercher dans les suivants
					// où il n'est d'ailleurs pas
					break;
				} catch (final InstanceNotFoundException e) {
					// ObjectName non trouvé dans ce MBeanServer, donc on
					// cherche dans le suivant
					// (nécessaire pour JBoss 5.0.x)
					instanceNotFoundException = e;
					continue;
				} catch (final JMException e) {
					throw new IllegalArgumentException(name + '.' + attribute, e);
				}
			}
			if (instanceNotFoundException != null) {
				throw new IllegalArgumentException(name + '.' + attribute, instanceNotFoundException);
			}
		}
		return result;
	}

	static String getConvertedAttributes(String jmxValueParameter) {
		final List<String> mbeanAttributes = Arrays.asList(jmxValueParameter.split("[" + ATTRIBUTES_SEPARATOR + ']'));
		final List<Object> jmxValues = getConvertedAttributes(mbeanAttributes);
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Object jmxValue : jmxValues) {
			if (first) {
				first = false;
			} else {
				sb.append(ATTRIBUTES_SEPARATOR);
			}
			sb.append(jmxValue);
		}
		return sb.toString();
	}

	private String getAttributeDescription(String name, MBeanAttributeInfo[] attributeInfos) {
		for (final MBeanAttributeInfo attributeInfo : attributeInfos) {
			if (name.equals(attributeInfo.getName())) {
				// certaines descriptions d'attributs comme les NamingResources
				// dans Tomcat 7 contiennent aussi des espaces qui se suivent
				final String attributeDescription = formatDescription(attributeInfo.getDescription());
				if (attributeDescription == null || name.equals(attributeDescription)
						|| attributeDescription.isEmpty()) {
					// les attributs des MBeans de java.lang ont des
					// descriptions égales aux noms,
					// ce sont des descriptions inutiles
					return null;
				}
				return attributeDescription;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return MBeanServer
	 */
	static MBeanServer getPlatformMBeanServer() {
		// ManagementFactory类是用于获取Java平台的托管Bean的工厂类。
		// On the first call to this method, it first creates the platform
		// MBeanServer by calling the MBeanServerFactory.createMBeanServer
		// method and registers the platform MXBeans in this platform
		// MBeanServer using the MXBean names defined in the class description.
		// This method, in subsequent calls, will simply return the initially
		// created platform MBeanServer.
		return ManagementFactory.getPlatformMBeanServer();
		// alternative (sauf pour Jenkins slaves):
		// final List<MBeanServer> mBeanServers =
		// MBeanServerFactory.findMBeanServer(null);
		// if (!mBeanServers.isEmpty()) {
		// // il existe déjà un MBeanServer créé précédemment par Tomcat ou bien
		// ci-dessous
		// return mBeanServers.get(0);
		// }
		// final MBeanServer server = MBeanServerFactory.createMBeanServer();
		// return server;
	}

	/**
	 * Retourne la liste de tous les javax.management.MBeanServer.
	 * 
	 * @return List
	 */
	private static List<MBeanServer> getMBeanServers() {
		// Return a list of registered MBeanServer objects. A registered
		// MBeanServer object is one that was created by one of the
		// createMBeanServer methods and not subsequently released with
		// releaseMBeanServer.
		//findMBeanServer:要检索的MBeanServer的代理标识符。如果此参数为空，则返回此JVM中的所有已注册的MBeanServers
		return MBeanServerFactory.findMBeanServer(null);
	}
	public static void main(String args[]) throws JMException{
		MBeans bean=new MBeans();
		System.out.println(bean.mbeanServer);
		Iterator<MBeanNode> it=bean.getMBeanNodes().iterator();
		while(it.hasNext()){		
			MBeanNode b=it.next();
			System.out.println(b.getName());
		}
	}
}
