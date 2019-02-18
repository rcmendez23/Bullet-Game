interface IList<T> {
  // Map Function
  <U> IList<U> map(IFunc<T, U> f);
}

class MtList<T> implements IList<T> {

  //Returns the MtList because there are no items to map
  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  //Applies function f onto every item in the cons
  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }
}

interface IPred<T> {
  boolean apply(T t);
}

interface IFunc<A, R> {
  R apply(A arg);
}




