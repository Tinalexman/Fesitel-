import os
from ransomware import Ransomware

ransomware = Ransomware()

class Main:
    def __init__(self):
        self.path_directory = "/home/testys/Documents/Fesitel--master/lib"

    def run(self):
        ransomware.set_mode(ransomware.Mode.DECRYPT)
        ransomware.process(self.path_directory)


if __name__ == "__main__":
   main = Main()
   main.run()
   
