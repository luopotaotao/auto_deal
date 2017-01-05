from tkinter import *

root = Tk()

e = StringVar()

entry = Entry(root, textvariable=e)
e.set('input you text here')
entry.pack()
Button(textvariable=e).pack()
root.mainloop()
