# 排序算法总结

| | 平均时间复杂度 | 原地排序 | 额外空间 | 稳定性 |
| -- | -- | -- |  -- | -- |
| 插入排序| O(n^2) | 是 | O(1) | 稳定 | 
| 归并排序| O(nlogn) | 否 | O(n) | 稳定 |
| 快速排序| O(nlogn) | 是 | O(logn) | 不稳定 |
| 堆排序| O(nlogn) | 是 | O(1) | 不稳定 |

## 排序算法的稳定性
稳定排序：对于相等的元素，在排序后，原来靠前的元素依然靠前，相等元素的相对位置没有发生改变。快排因为pivot是随机选择的，因此有可能使相等的元素排序前后次序不同。堆排序shiftDown，shiftUp的过程同样会影响排序的稳定性。

# 排序拓展
## 索引堆
