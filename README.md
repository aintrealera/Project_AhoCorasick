# Aho-Corasick Algorithm in Java

## 1. Project Description

This project implements the Aho-Corasick string matching algorithm in Java.

The algorithm searches for multiple patterns simultaneously in a given text.

The project also compares Aho-Corasick with the Knuth-Morris-Pratt (KMP) algorithm.

## 2. Implementation

The implementation consists of two main classes:

- AhoCorasick.java — implements the Aho-Corasick algorithm.
- Benchmark.java — generates test data, measures execution time, and compares Aho-Corasick with KMP.

The Aho-Corasick implementation uses three main components:

**Trie:** A tree structure that stores all patterns.

**Failure Links:** Links that help the algorithm continue searching from another state when a character does not match.

**Output Links:** Links that allow the algorithm to find additional patterns ending at the same position.

## 3. Experimental Setup

The experiments use the following parameters:

Number of patterns:
- 10
- 100
- 1,000

Text lengths:
- 1,000 characters
- 10,000 characters
- 100,000 characters

Each experiment is repeated 5 times.

Execution time is measured using System.nanoTime().

The average execution time is calculated in milliseconds.

## 4. Experimental Results

The experiments were performed using 10, 100, and 1,000 patterns with text lengths of 1,000, 10,000, and 100,000 characters. Each experiment was repeated five times, and the average execution time was calculated using System.nanoTime().

| Patterns | Text Size | Aho-Corasick (ms) | KMP (ms) |
|---:|---:|---:|---:|
| 10 | 1,000 | 0.9161 | 2.0108 |
| 10 | 10,000 | 2.4309 | 3.8094 |
| 10 | 100,000 | 4.5024 | 10.9651 |
| 100 | 1,000 | 0.6396 | 1.0283 |
| 100 | 10,000 | 1.0202 | 19.5469 |
| 100 | 100,000 | 4.2144 | 111.7724 |
| 1,000 | 1,000 | 2.9944 | 10.0966 |
| 1,000 | 10,000 | 1.6898 | 111.3438 |
| 1,000 | 100,000 | 6.2538 | 1055.0360 |

## 5. Results Analysis

The experimental results show that Aho-Corasick performs better than KMP in all tested cases.

When the number of patterns increases, the execution time of KMP grows significantly because it searches for every pattern separately. For example, with 1,000 patterns and a text length of 100,000 characters, KMP takes 1055.0360 ms, while Aho-Corasick takes only 6.2538 ms.

This demonstrates that Aho-Corasick is especially effective when searching for a large number of patterns in the same text.

Aho-Corasick uses a Trie and failure links to process multiple patterns during a single traversal of the text. KMP must process the text separately for each pattern.

Small variations in execution time between experiments may occur because of JVM optimization, memory allocation, and system activity.

## 6. Time Complexity Analysis

Let:

- N = length of the text.
- M = total length of all patterns.
- K = number of patterns.
- Z = total number of matches.

### Aho-Corasick

- Trie construction: O(M)
- Failure links construction: O(M) expected with hash-based transitions.
- Searching: O(N + Z)

**Overall Time Complexity: O(M + N + Z)**

The algorithm processes the text in one traversal and uses failure links to avoid unnecessary repeated comparisons.

### KMP

For one pattern:

- Preprocessing (LPS array): O(m)
- Searching: O(N)

For K patterns searched separately:

**Overall Time Complexity: O(M + K × N)**

KMP must scan the text separately for every pattern, which increases execution time when the number of patterns grows.

## 7. Space Complexity Analysis

### Aho-Corasick

- Trie nodes and transitions: O(M)
- Failure links: O(M)
- Output links: O(M)
- Pattern storage: O(M)

**Overall Space Complexity: O(M)**

The algorithm requires additional memory to store the Trie and its links. This memory allows it to search for multiple patterns efficiently.

### KMP

- LPS array: O(m)
- Additional searching variables: O(1)

**Overall Space Complexity: O(m)**

When patterns are processed separately, the LPS array can be reused. Therefore, the auxiliary space complexity depends on the length of the longest pattern.

## 8. Conclusion

Based on the experiments, Aho-Corasick is significantly more efficient than running KMP separately for multiple patterns.

The biggest performance difference appears when searching for 1,000 patterns in a text containing 100,000 characters.

Aho-Corasick has a time complexity of O(M + N + Z) and a space complexity of O(M). KMP has a time complexity of O(M + K × N) when searching for multiple patterns separately.

Therefore, Aho-Corasick is a suitable algorithm for applications that require fast searching of multiple keywords in large amounts of text. KMP remains a good choice for searching for a single pattern because it is simpler and requires less additional memory.