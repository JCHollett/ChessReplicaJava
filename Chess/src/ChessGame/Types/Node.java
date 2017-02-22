package ChessGame.Types;

public class Node<K, V> extends Object {
	private K key;
	private V value;
	
	public Node(K k, V v) {
		this.key = k;
		this.value = v;
	}
	
	public K getKey() {
		return this.key;
	}
	
	public Node<K, V> getNode() {
		return this;
	}
	
	public V getValue() {
		return this.value;
	}
	
	public K setKey(K k) {
		this.key = k;
		return this.key;
	}
	
	public Node<K, V> setNode(K k, V v) {
		this.setKey(this.key);
		this.setValue(v);
		return this;
	}
	
	public boolean equals(Object o) {
		@SuppressWarnings("unchecked")
		Node<K, V> objNode = (Node<K, V>)o;
		if (this.getValue().toString().equalsIgnoreCase(objNode.getValue().toString())) {
			if (this.getKey().toString().equalsIgnoreCase(objNode.getKey().toString())) {
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}
	
	public V setValue(V v) {
		this.value = v;
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[key=" + this.key + ",value=" + this.value + "]";
	}
}
