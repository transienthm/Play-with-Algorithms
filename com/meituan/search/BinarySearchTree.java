package com.meituan.search;


public class BinarySearchTree {
	private Node root;
	int count;

	/**
	* Node设计为内部类，所有返回Node的方法都为private，对外界来说，不需要感知到Node的存在
	*/
	class Node<K, V> {
		K key;
		V value;
		Node left;
		Node right;
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = this.right = null;
		}
	}

	public BinarySearchTree() {
		this.root = null;
		this.count = 0;
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void insert(K key, V value) {
		root = insert(root, key, value);
	}

	private Node insert(Node node, K key, V value) {
		if (node == null) {
			count++;
			return new Node(key, value);
		}

		if (key == node.key) {
			node.value = value;
		} else if (key < node.key) {
			node.left = insert(node.left, key, value);
		} else {
			node.right = insert(node.right, key, value);
		}

		return node;
	}

	public boolean contain(K key) {
		return contain(root, key);
	}

	private boolean contain(Node root, K key) {
		if (root == null) {
			return false;
		}

		if (key == node.key) {
			return true;
		} else if (key < root.key) {
			return contain(root.left, key);
		} else {
			return contain(root.right, key);
		}
	}

	/**
	* 返回值的方式
	* 1. 返回node，不好Node为内部类
	* 2. 返回Value
	*/
	public V search(K key) {
		return search(root, key);
	}

	private V search(Node root, K key) {
		if (root == null) {
			return null;
		} 

		if (key == root.key) {
			return root.value;
		} else if (key < root.key) {
			return search(root.left, key)
		} else {
			return search(root.right, key);
		}
	}
}