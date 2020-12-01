import time

WORD_MASK = 65535

def print_cur_time():
    cur_time = round(time.time() * 1000)
    
    print(time.time())
    print(cur_time)
    
    as_3_words(cur_time)

def as_3_words(x):
    gsb = (x & (WORD_MASK << 32)) >> 32
    mid = (x & (WORD_MASK << 16)) >> 16
    lsb = x & WORD_MASK
    return (gsb, mid, lsb)

def from_3_words(x):
    (gsb, mid, lsb) = x
    return (gsb << 32) | (mid << 16) | lsb

def as_2_words(x):
    print(f"{x:b}")
    lsb = x & WORD_MASK
    gsb = (x & (WORD_MASK << 16)) >> 16
    return (gsb, lsb)

internal_times = [
    (374, 7925, 46782),
    (373, 945, 28),
    (370, 7917, 5178),
    (248, 2645, 13484),
    (177, 8921, 55155),
    (450, 4783, 28305),
]

for val in internal_times:
    print(val)
    print(from_3_words(val))
    print()


