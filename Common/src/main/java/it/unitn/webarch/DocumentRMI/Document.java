package it.unitn.webarch.DocumentRMI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: DocumentRMI
 * Created by en on 15/10/17.
 */
public class Document implements Serializable{
	private static final long serialVersionUID = 1L;
	List<String> items;

	public Document(){
		this.items = new ArrayList<>();
	}

	public void addString(String item){
		items.add(item);
	}

	public String toString(){
		if(this.items == null) return "";
		if(this.items.isEmpty()) return "";

		final StringBuilder res = new StringBuilder(this.items.get(0));
		for(String item : this.items.subList(1, this.items.size())){
			res.append(" ").append(item);
		}
		return res.toString();
	}
}
