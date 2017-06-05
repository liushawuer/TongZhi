/**
 * 
 */
package org.example.tongzhi;

import java.io.Serializable;
import java.util.List;


public class Record implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7268039419235980747L;
	private String id = null;
	private String title = null;
	private String author = null;
	private long pubTime = 0l;
	private List<String> files = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getPubTime() {
		return pubTime;
	}
	public void setPubTime(long pubTime) {
		this.pubTime = pubTime;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "Record{" +
				"title='" + title + '\'' +
				", id='" + id + '\'' +
				", author='" + author + '\'' +
				", pubTime=" + pubTime +
				", files=" + files +
				'}';
	}
}
