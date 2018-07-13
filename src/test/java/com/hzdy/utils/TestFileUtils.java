package com.hzdy.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
* 
* 测试类
* 
* @author kirohuji
* @version 0.1
*/
public class TestFileUtils {
	
	@Test
	@Ignore
	public void testFileCreate() {
		assertTrue("文件创建失败",FileUtils.create("test.json"));
	}
	@Test
	@Ignore
	public void testFileDelete() {
		assertTrue("文件删除失败",FileUtils.delete("test.json"));
	}
	@Test
	@Ignore
	public void testFileWrite() {
		assertTrue("文件写入失败",FileUtils.write("test.json","over"));
	}
	@Test
	public void testFileAppend() {
		assertTrue("文件写入失败",FileUtils.append("test.json","over"));
		System.out.println("写入成功");
	}
}
