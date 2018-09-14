from functools import reduce

def compose(*fns):
    return reduce(lambda g, f: lambda x: f(g(x)), fns, lambda x: x)