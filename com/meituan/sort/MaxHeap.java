package com.meituan.sort;

import java.util.Random;
import java.lang.reflect.Array;

public class MaxHeap<Item extends Comparable> {
	private Item[] data;
	private int count;
	private int capacity;
	private Class<Item> type;

	public MaxHeap(int capacity, Class<Item> type) {
		//泛型数组的经典处理方式 
		this.data = (Item[]) Array.newInstance(type, capacity + 1);//索引0不存储数据
		this.count = 0;
		this.capacity = capacity;
		this.type = type;	
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public void insert(Item item) {
		//首先要保证数组不越界
		if (this.count + 1 >= this.capacity) {
			this.capacity = this.capacity * 2 + 1;
			Item[] newData = (Item[]) Array.newInstance(type, capacity);
			System.arraycopy(data, 0, newData, 0, count + 1);
			data = newData;
		}

		data[++count] = item;
		shiftUp(count);
	}

	public Item extractMax() {
		if (count <= 0) {
			return null;
		}

		Item res = data[1];
		swap(data, 1, count--);
		shiftDown(1);

		return res;
	}

	private void shiftUp(int k) {
		while (k > 1 && data[k / 2].compareTo(data[k]) < 0) {
			swap(data, k / 2, k);
			k /= 2;
		}
	}

	private void shiftDown(int k) {
		while (k <= (count - 1) / 2 && data[k].compareTo(max(data[k * 2], data[k * 2 + 1])) < 0) {
			if (data[k * 2].compareTo(data[k * 2 + 1]) > 0) {
				swap(data, k, k * 2);
				k = k * 2;
			} else {
				swap(data, k, k * 2 + 1);
				k = k * 2 + 1;
			}
		}
	}

	private void swap(Item[] arr, int i, int j) {
		Item t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	private Item max(Item a, Item b) {
		if (a == null) {
			return b;
		}

		if (b == null) {
			return a;
		}

		if (a.compareTo(b) > 0) {
			return a;
		} else {
			return b;
		}
	}

	public static void main(String[] args) {
		MaxHeap<Integer> maxHeap = new MaxHeap<>(1, Integer.class);
		int n = 16;
		long seed = System.nanoTime();
		Random random = new Random(seed);

		for (int i = 0; i < n; i++) {
			maxHeap.insert(Math.abs(random.nextInt() % 10));
		}
		maxHeap.treePrint();
		System.out.println("extractMax()操作开始执行：");
		while (!maxHeap.isEmpty()) {
			System.out.print(maxHeap.extractMax() + " ");
		}
	}

	/**
	* 打印树相关算法
	*/
 	public void treePrint(){

        if( size() >= 100 ){
            System.out.println("This print function can only work for less than 100 integer");
            return;
        }

        System.out.println("The max heap size is: " + size());
        System.out.println("Data in the max heap: ");
        for( int i = 1 ; i <= size() ; i ++ ){
            // 我们的print函数要求堆中的所有整数在[0, 100)的范围内
            assert (Integer)data[i] >= 0 && (Integer)data[i] < 100;
            System.out.print(data[i] + " ");
        }
        System.out.println();
        System.out.println();

        int n = size();
        int maxLevel = 0;
        int numberPerLevel = 1;
        while( n > 0 ){
            maxLevel += 1;
            n -= numberPerLevel;
            numberPerLevel *= 2;
        }

        int maxLevelNumber = (int)Math.pow(2, maxLevel-1);
        int curTreeMaxLevelNumber = maxLevelNumber;
        int index = 1;
        for( int level = 0 ; level < maxLevel ; level ++ ){

            String line1 = new String(new char[maxLevelNumber*3-1]).replace('\0', ' ');

            int curLevelNumber = Math.min(count-(int)Math.pow(2,level)+1,(int)Math.pow(2,level));
            boolean isLeft = true;
            for( int indexCurLevel = 0 ; indexCurLevel < curLevelNumber ; index ++ , indexCurLevel ++ ){
                line1 = putNumberInLine( (Integer)data[index] , line1 , indexCurLevel , curTreeMaxLevelNumber*3-1 , isLeft );
                isLeft = !isLeft;
            }
            System.out.println(line1);

            if( level == maxLevel - 1 )
                break;

            String line2 = new String(new char[maxLevelNumber*3-1]).replace('\0', ' ');
            for( int indexCurLevel = 0 ; indexCurLevel < curLevelNumber ; indexCurLevel ++ )
                line2 = putBranchInLine( line2 , indexCurLevel , curTreeMaxLevelNumber*3-1 );
            System.out.println(line2);

            curTreeMaxLevelNumber /= 2;
        }
    }

    private String putNumberInLine( Integer num, String line, int indexCurLevel, int curTreeWidth, boolean isLeft){

        int subTreeWidth = (curTreeWidth - 1) / 2;
        int offset = indexCurLevel * (curTreeWidth+1) + subTreeWidth;
        assert offset + 1 < line.length();
        if( num >= 10 )
            line = line.substring(0, offset+0) + num.toString()
                    + line.substring(offset+2);
        else{
            if( isLeft)
                line = line.substring(0, offset+0) + num.toString()
                        + line.substring(offset+1);
            else
                line = line.substring(0, offset+1) + num.toString()
                        + line.substring(offset+2);
        }
        return line;
    }

    private String putBranchInLine( String line, int indexCurLevel, int curTreeWidth){

        int subTreeWidth = (curTreeWidth - 1) / 2;
        int subSubTreeWidth = (subTreeWidth - 1) / 2;
        int offsetLeft = indexCurLevel * (curTreeWidth+1) + subSubTreeWidth;
        assert offsetLeft + 1 < line.length();
        int offsetRight = indexCurLevel * (curTreeWidth+1) + subTreeWidth + 1 + subSubTreeWidth;
        assert offsetRight < line.length();

        line = line.substring(0, offsetLeft+1) + "/" + line.substring(offsetLeft+2);
        line = line.substring(0, offsetRight) + "\\" + line.substring(offsetRight+1);

        return line;
    }
}