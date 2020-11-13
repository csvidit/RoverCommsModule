def encode(x):
    for exponent in range(0, 4):  # Valid exponents are [0, 3]
        if x < 2**14 * 2**exponent:
            return (exponent, round(x / 2**exponent))
    raise Exception("x was too big!")

def decode(x):
    (exponent, significand) = x
    return significand * 2**exponent

for num in [64, 16389, 54823, 96737, 124832, 113877]:
    print(num)
    encoded = encode(num)
    print(encoded)
    print(decode(encoded))
    print()

