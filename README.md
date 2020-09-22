# Word file repair

Small Java project to check deleted but recovered doc and docx files. 

## Goal of this project

On the computer of my parents the documents directory disappeared. Not clear what happened but somehow
this complete directory was deleted. Using [Disk Drill](https://www.cleverfiles.com/data-recovery-software.html)
I was able to find back a very long list of .doc and .docx files, but most of those were unreadable, corrupted
or already overwritten by other data and unreadable. An other problem: the original filename of the recovered files 
is lost, and got renamed to "file000000.doc", "file000001.doc",...

To avoid manual opening of each file, this application tries to read from the recovered files and if
text could be found, it is used to create a new file with a recognizable name. This way only the valid 
files are copied to a new directory.

## Inspired by 

* [How to read doc and docx file in java with POI api](https://stackoverflow.com/questions/17617173/how-to-read-doc-and-docx-file-in-java-with-poi-api)
* [Recursively list files in Java](https://stackoverflow.com/questions/2056221/recursively-list-files-in-java)