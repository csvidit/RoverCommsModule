# Command Message Tests

# In
The files in `in/` will be whatever format is consumed by the java message
encoder, and they should correspond to the files in `out/`.

# Out
The files in `out/` each contain a set of numbers that correspond to entries in
the test plan. Each number corresponds to a 1-word field in the "uncompressed"
command struct in `command.pep` (and are orded the same).

