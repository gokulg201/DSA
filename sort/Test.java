//$Id$
package sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	boolean isDeadlock(Map<String,List<String>> map){
	    for(String key:map.keySet()){
	       List<String> keyCount=new ArrayList<String>();
	       keyCount.add(key);
	       if(check(map.get(key),keyCount,map))return true;
	    }    
	    return false;
	}

	boolean check(List<String> values,List<String> keyCount,Map<String,List<String>> map){
        for(String value:values){
            if(keyCount.contains(value)) return true;
            	keyCount.add(value);
            	if(map.containsKey(value)){
            		List<String> v=map.get(value);
	                return check(v,keyCount,map);
            	}else{
            		return false;
            	}
            }
        return false;
	}
	public static void main(String[] args){
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		List<String> a= new ArrayList<String>();
		a.add("h");
		a.add("i");
		map.put("a", a);
		List<String> b= new ArrayList<String>();
		b.add("c");
		map.put("b", b);
		List<String> c= new ArrayList<String>();
		c.add("e");
		map.put("c", c);
		List<String> h= new ArrayList<String>();
		h.add("d");
		map.put("h", h);
		List<String> d= new ArrayList<String>();
		d.add("c");
		map.put("d", d);
		Test t= new Test();
		System.out.println(t.isDeadlock(map));
	}
		
}
