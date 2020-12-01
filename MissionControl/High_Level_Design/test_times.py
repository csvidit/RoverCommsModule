import time

WORD_MASK = 65535

def print_cur_time():
    
    cur_time = round(time.time() * 1000)
    
    print(time.time())
    print(cur_time)
    
    print("(Big Endian)")
    print((cur_time & (WORD_MASK << 32)) >> 32)
    print((cur_time & (WORD_MASK << 16)) >> 16)
    print(cur_time & WORD_MASK)

def as_2_words(x):
    print(f"{x:b}")
    lsb = x & WORD_MASK
    gsb = (x & (WORD_MASK << 16)) >> 16
    return (gsb, lsb)

print(as_2_words(86400))

