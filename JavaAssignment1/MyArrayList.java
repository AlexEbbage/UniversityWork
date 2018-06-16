package ass;

public class MyArrayList<E> {
	
	private Object[] array;
	private int size = 0;
	private static int defaultSize = 5;
	
	
	public MyArrayList(){
		this(defaultSize);
	}

	
	public void add(E element){
		for(int i=0; i<size; i++){
			if(array[i] == null){
				array[i] = element;
				return;
			}
		}
		ensureCapacity(size);
		array[size++] = element;
	}


	private void ensureCapacity(int size){
		System.out.println(size + " " + array.length);
		if(size < array.length){
			return;
		}
		resize();
	}


	private void resize(){
		Object[] temp = new Object[array.length*2];
		copy(array,temp);
		array = temp;
	}


	private void copy(Object[] oldArray, Object[] newArray){
		if(newArray.length< oldArray.length){
			throw new RuntimeException(oldArray+ " cannot be copied into "+newArray);
		}
		for(int i=0;i<oldArray.length; i++){
			newArray[i] = oldArray[i];
		}   
	}

	
	public MyArrayList(int size) {
        if (size < 0){
            throw new IllegalArgumentException("Illegal Capacity: "+ size);
        }
        this.size = size;
		array = new Object[size];
	}
	
	
	public int size(){
	    return this.size;
	}
	
	
	public E get(int index){
        checkRange(index);
	    E element = (E) array[index];
	    return element;
	}


	public boolean contains(E object){
		for (int i=0; i<size; i++) {
			E element = (E) array[i];
			if ( (object==null) ? element==null : object.equals(element) )
				return true;
		}
		return false;
	}


	public void set(int index,E object){
        checkRange(index);
		E old = (E) array[index];    
		array[index] = object;
	}


	public int indexOf(E object){
		for (int i=0; i<size; i++) {
			E element = (E) array[i];
			if ( (object==null) ? element==null : object.equals(element) )
				return i;
		}
		return -1;
	}


    public void add(int index, E object) {
		if (index < 0 || index > size){
            throw new ArrayIndexOutOfBoundsException();
        }
        ensureCapacity(size + 1);
        for (int i = size; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = object;
        size++;
    }


    private void checkRange(int index) {
		if (index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

	
	public boolean remove(E object){
        int index = indexOf(object);
        if(index == -1){
        	return false;
        }
        else{
        	remove(index);
        	return true;
        }
	}
	
	
	public E remove(int index){
        checkRange(index);
		E element = (E) array[index];
	    for (int i = index; i < size; i++){
	        array[i] = array[i + 1];
	    }
		size--;
		compress();
		return element;
	}


	private void compress(){
		int skipCount =0;
		for(int i=0;i < size && i<array.length; i++){
			if(array[i]==null){              
			}
			array[i]=array[i+skipCount];
    }
}
	
	
	
	
}
	
	
	

