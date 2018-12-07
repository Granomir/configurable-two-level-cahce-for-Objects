# configurable-two-level-cahce-for-Objects

It's a configurable two-level cache for caching Objects. Level 1 is RAM, level 2 is file system. While creating an object 
of the cahce, you can configure capacities of both levels and set caching algorithm (LFU, LRU or MRU).

When you put object into cahce (also passing a key, which will let you to get the object back), it gets into the 1 level firstly. 
If there is no empty place for new object in 1 level, the weakest object (according to the selected caching algorithm)  
from 1 level will be moved to 2 level. After that, the new object will get into 1 level.

When you try to get an object from cahce and it is located in 2 level, it would be moved to 1 level firstly and then handed to you.

The rules, described above, let to keep more popular objects in 1 level of cahce, which works faster (RAM memory).

Used technologies - Maven, JSON, JUnit, SLF4J.