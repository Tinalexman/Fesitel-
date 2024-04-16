import random

class KeyGenerator:
    SEED = 98942897316645566
    CHARACTER_POOL = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"

    @staticmethod
    def generate_unique_keys(rounds):
        random.seed(KeyGenerator.SEED)
        keys = []

        for i in range(rounds):
            first_char = KeyGenerator.CHARACTER_POOL[random.randint(0, len(KeyGenerator.CHARACTER_POOL) - 1)]
            second_char = KeyGenerator.CHARACTER_POOL[random.randint(0, len(KeyGenerator.CHARACTER_POOL) - 1)]
            input = (ord(first_char) << 8) | ord(second_char)

            if i == 0:
                response = KeyGenerator.generate_rail_fence_cipher_key(input)
            elif i == 1:
                response = KeyGenerator.generate_columnar_cipher_key(input)
            else:
                response = KeyGenerator.generate_vigenere_cipher_key(input)

            keys.append(response)
        return keys

    @staticmethod
    def generate_rail_fence_cipher_key(input):
        return input %len(KeyGenerator.CHARACTER_POOL)

    @staticmethod
    def generate_columnar_cipher_key(input):
        return int('{:032b}'.format(input)[::-1], 2)

    @staticmethod
    def generate_vigenere_cipher_key(input):
        return ~input
