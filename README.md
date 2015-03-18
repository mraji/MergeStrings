This class implement an overlap String algorithm.

For each input line, search the collection of fragments to locate the pair with the maximal overlap match then merge those two fragments. This operation will decrease the total number of fragments by one. Repeat until there is only one fragment remaining in the collection. This is the defragmented line / reassembled document.

If there is more than one pair of maximally overlapping fragments in any iteration then just merge one of them. So long as you merge one maximally overlapping pair per iteration the test inputs are guaranteed to result in good and deterministic output.

When comparing for overlaps, compare case sensitively.
To build : java -g MarouaneRaji.java
To test : java fragment.submissions.MarouaneRaji sample2.txt
