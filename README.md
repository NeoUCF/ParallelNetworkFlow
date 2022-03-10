# Network Flow Parallelization
This is a research project for COP_4520: Concepts of Parallel and Distributed Processing.

We are exploring how to parallelize common network flow algorithms and empirically test the benefits of making it parallel.

The following are the algorithms and the group members working on making it parallel:
### Ford-Fulkerson Algorithm
#### John Pham and Neo Camanga

### Edmonds-Karp Algorithm
#### Glenn Hartwell

### Dinics Algorithm
#### Brian Moon and Federico Baron

## Running the Program
The testing datasets 50v.in through 1000v.in come from <https://github.com/SumitPadhiyar/parallel_ford_fulkerson_gpu/tree/master/dataset>

To run a program, follow the following paradigm:
`$ java FordFulkerson.java`

It will ask for a path:
`$ ../dataset/50v.in`

It will ask for number of nodes:
`$ 50`

It will ask for thread limit:
`$ 8`