import time

WORD_MASK = 65535

cur_time = round(time.time() * 1000)

print(time.time())
print(cur_time)

print("(Big Endian)")
print((cur_time & (WORD_MASK << 32)) >> 32)
print((cur_time & (WORD_MASK << 16)) >> 16)
print(cur_time & WORD_MASK)

