package com._604robotics.robotnik.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FuzzyMatcher {
	private class CacheEntry {
		public String str1;
		public String str2;
		public CacheEntry(String str1, String str2) {
			this.str1=str1;
			this.str2=str2;
		}
	}

	private LinkedHashMap<CacheEntry,Integer> MatchLRUCache;

	public int FuzzyMatcher(String str1, String str2) {
		CacheEntry norm = new CacheEntry(str1,str2);
		CacheEntry rev = new CacheEntry(str2,str1);
		/* See if in cache */
		if (MatchLRUCache.containsKey(norm) || MatchLRUCache.containsKey(rev)) {
			/* Assert same distance for both orderings */
			if (MatchLRUCache.get(norm) == MatchLRUCache.get(rev)) {
				return MatchLRUCache.get(norm);
			} else {
				/* Mapping is corrupted -> toss out both entries */
				MatchLRUCache.remove(norm);
				MatchLRUCache.remove(rev);
			}
		} else /* Add to cache */ {
			
		}
		return 0;
	}
}
