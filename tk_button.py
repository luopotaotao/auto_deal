from tkinter import *

root = Tk()


def log():
    print('hello')


for i in ['flat', 'groove', 'raised', 'ridge', 'solid', 'sunken']:
    Button(master=root, text=i, command=log, relief=i).pack()

root.mainloop()
