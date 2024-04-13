class FeistelAlgorithm:
    ROUNDS = 3

    def __init__(self):
        unique_keys = []

    def initialize(self, keys):
        self.unique_keys = keys

    def combine(self, value, key):
        response = (value * 2) | 0b00001011
        response ^= key
        response >>= 2
        response &= 0x00FF
        return response

    def encrypt_round(self, value, key):
        l0 = value >> 16
        r0 = (value & 0xFFFF)
        E = self.combine(r0, key)
        l1 = r0
        r1 = l0 ^ E
        return (l1 << 16) | r1

    def decrypt_round(self, value, key):
        l1 = value >> 16
        r1 = (value & 0xFFFF)
        r0 = l1
        E = self.combine(r0, key)
        l0 = r1 ^ E
        return (l0 << 16) | r0

    def encrypt_data(self, data):
        encrypted_data = []
        for value in data:
            modified_value = value
            for round in range(self.ROUNDS):
                key = self.unique_keys[round]
                modified_value = self.encrypt_round(modified_value, key)
            encrypted_data.append(modified_value)
        return encrypted_data

    def decrypt_data(self, data):
        decrypted_data = []
        for value in data:
            modified_value = value
            for round in range(self.ROUNDS):
                key = self.unique_keys[FeistelAlgorithm.ROUNDS - round - 1]
                modified_value = self.decrypt_round(modified_value, key)
            decrypted_data.append(modified_value)
        return decrypted_data
