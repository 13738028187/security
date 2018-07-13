package com.hzdy.hardware.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Bean
public class MBeanNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String name;

	private final String description;

	private final List<MBeanNode> children;

	private final List<MBeanAttribute> attributes;
	//BeanAttribuate
	public static class MBeanAttribute implements Serializable {
		private static final long serialVersionUID = 1L;

		private final String name;

		private final String description;

		private final String formattedValue;

		MBeanAttribute(String name, String description, String formattedValue) {
			super();
			this.name = name;
			this.description = description;
			this.formattedValue = formattedValue;
		}

		String getName() {
			return name;
		}

		String getDescription() {
			return description;
		}

		String getFormattedValue() {
			return formattedValue;
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return getClass().getSimpleName() + "[name=" + getName() + ", formattedValue="
					+ getFormattedValue() + ']';
		}
	}

	MBeanNode(String name) {
		super();
		this.name = name;
		this.description = null;
		this.children = new ArrayList<MBeanNode>();
		this.attributes = null;
	}

	MBeanNode(String name, String description, List<MBeanAttribute> attributes) {
		super();
		this.name = name;
		this.description = description;
		this.children = null;
		this.attributes = attributes;
	}

	String getName() {
		return name;
	}

	String getDescription() {
		return description;
	}

	List<MBeanNode> getChildren() {
		return children != null ? children : null;
	}

	List<MBeanAttribute> getAttributes() {
		return attributes != null ? attributes : null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name=" + getName() + ']';
	}
}