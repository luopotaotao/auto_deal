from tkinter import *

root = Tk()


def change_text():
    if b['text'] == 'abc':
        v.set('change')
    else:
        v.set('abc')


v = StringVar()
b = Button(root, textvariable=v, command=change_text);
b.pack()

root.mainloop()
