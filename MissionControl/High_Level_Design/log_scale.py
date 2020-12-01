def encode(x):
    for exponent in range(0, 4):  # Valid exponents are [0, 3]
        if x < 2**14 * 2**exponent:
            return (exponent, round(x / 2**exponent))
    raise Exception("x was too big!")

def encoded_as_int(encoded):
    (exponent, significand) = encoded
    return (exponent << 14) | significand

def decode(x):
    (exponent, significand) = x
    return significand * 2**exponent

for num in [16, 235, 1987, 6874, 27543, 75654, 99991]:
    print(num)
    encoded = encode(num)
    print(encoded)
    print(encoded_as_int(encoded))
    print(f"{encoded_as_int(encoded):b}")
    print(decode(encoded))
    print()

