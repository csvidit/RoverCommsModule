# Status Message Tests

# In
The files in `in/` each contain a set of numbers that correspond to
entries in the test plan. Each number corresponds to a 1-word field in the
"uncompressed" status struct in `status.pep` (and are ordered the same).

Note that any other characters beyond newlines/spaces will probably screw things
up, the parser is not particularly sophisticated.

# Out
The files in `out/` will be the same as whatever format is outputted by the java
program that decodes the status messages, corresponding to the inputs in `in/`.

