package org.akka.essentials.wc.mapreduce.example.myWordCount;

import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReduceActor extends UntypedActor {

	private Map<String, Integer> finalReducedMap = new HashMap<String, Integer>();

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Map) {
			Map<String, Integer> reducedList = (Map<String, Integer>) message;
			aggregateInMemoryReduce(reducedList);
		} else if (message instanceof String) {
			if (((String) message).compareTo("DISPLAY_LIST") == 0) {
				System.out.println(finalReducedMap.toString());
			}
		}
	}

	private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {

		Iterator<String> iter = reducedList.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			if (finalReducedMap.containsKey(key)) {
				Integer count = reducedList.get(key) + finalReducedMap.get(key);
				finalReducedMap.put(key, count);
			} else {
				finalReducedMap.put(key, reducedList.get(key));
			}

		}
	}

}
