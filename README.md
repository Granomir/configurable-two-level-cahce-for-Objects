# configurable-two-level-cache-for-Objects

Used technologies - Maven, JSON, JUnit, SLF4J.

It's a configurable two-level cache for caching Objects. Level 1 is RAM, level 2 is file system. While creating an object 
of the cache, you can configure capacities of both levels and set caching algorithm (LFU, LRU or MRU).

When you put object into cache (also passing a key, which will let you to get the object back), it gets into the level 1 firstly.
If there is no empty place for new object in level 1, the weakest object (according to the selected caching algorithm)  
from level 1 will be moved to level 2. After that, the new object will get into level 1. If cache is full, the weakest element 
will be deleted.

If specified key already exists in the cache, it's value will be updated.

When you try to get an object from cache and it is located in level 2, it would be moved to level 1 firstly and then handed to you.

The rules, described above, let to keep more popular objects in level 1 of cache, which works faster (RAM memory).