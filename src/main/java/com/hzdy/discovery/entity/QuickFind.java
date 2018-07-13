package com.hzdy.discovery.entity;

import java.util.ArrayList;

public class QuickFind{
	private ArrayList<Integer> pd;
	private ArrayList<String> id;
	private ArrayList<Integer> sz;
	private int count;
	public QuickFind() {
		count=0;
		id=new ArrayList<>();
		pd=new ArrayList<>();
		sz=new ArrayList<Integer>();
	}
	public QuickFind(ArrayList<String> list) {
		count=list.size();
		id=new ArrayList<>();
		pd=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			pd.add(i);
		}
		//数据
		id.addAll(list);
		sz=new ArrayList<Integer>();
		//
		for(int i=0;i<list.size();i++) {
			sz.add(1);
		}
	}
	public boolean addValue(String value) {
		pd.add(count);
		count++;
		//数据
		id.add(value);
		sz.add(1);
		return true;
	}
	public ArrayList<Integer> getPd() {
		return pd;
	}
	public void setPd(ArrayList<Integer> pd) {
		this.pd = pd;
	}
	public ArrayList<String> getId() {
		return id;
	}
	public void setId(ArrayList<String> id) {
		this.id = id;
	}
	public ArrayList<Integer> getSz() {
		return sz;
	}
	public void setSz(ArrayList<Integer> sz) {
		this.sz = sz;
	}
	public void union(String p,String q) {
		int x=0;
		int y=0;
		for(int i=0;i<id.size();i++) {
			if(id.get(i).equals(p)) {
				x=i;
				continue;
			}
			if(id.get(i).equals(q)) {
				y=i;
				continue;
			}
		}
		int m=find(x);
		int n=find(y);
		if(m==n)
			return ;
		if(sz.get(m)<sz.get(n)) {
			pd.set(m, n);
			sz.set(n, sz.get(m)+1);
		}else {
			pd.set(n, m);
			sz.set(m, sz.get(n)+1);
		}
		
	}
	public int find(int p) {
		if(p==-1) {
			System.out.println("没有此ip");
		}
		while(p!=pd.get(p)) {
		      pd.set(p, pd.get(pd.get(p)));
		      p=pd.get(p);
		}
		return p;
	}
	public boolean connected(String p,String q) {
		int m=0,n=0;
		for(int i=0;i<id.size();i++) {
			if(id.get(i).equals(p)) {
				
			}
			if(id.get(i).equals(q)) {
				
			}
		}
		return find(m)==find(n);
		
	}
	public int count() {
		return count;	
	}
	public static void main(String args[]) {
		ArrayList<String> list=new ArrayList<>();
		list.add("192.168.1.1");
		list.add("192.168.1.23");
		list.add("192.168.1.3");
		list.add("192.168.1.123");
		list.add("192.168.1.10");
		list.add("10.0.0.1");
		list.add("10.0.0.3");
		list.add("192.168.1.24");
		list.add("192.168.1.25");
		QuickFind qf=new QuickFind(list);
		qf.getId().stream().forEach(System.out::println);
		qf.getPd().stream().forEach(System.out::println);
		qf.union("10.0.0.1", "10.0.0.3");
		qf.union("192.168.1.123", "10.0.0.3");
		qf.union("10.0.0.3", "192.168.1.24");
		qf.getPd().stream().forEach(System.out::println);
	}
}
