import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue1<E> implements BlockingQueue<E>{
    private final E[] array;
    // 首指针
    private int head;
    // 尾指针
    private int tail;
    private AtomicInteger size = new AtomicInteger();
    private ReentrantLock tailLock = new ReentrantLock();
    private ReentrantLock headLock = new ReentrantLock();
    private Condition headWaits = headLock.newCondition();
    private Condition tailWaits = tailLock.newCondition();


    public BlockingQueue1(int capacity) {
        array = (E[]) new Object[capacity];
    }

    @Override
    public void offer(E e) throws InterruptedException {
        tailLock.lockInterruptibly();
        int c;
        try {
            while (isFull()) {
                tailWaits.await();
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            c = size.getAndIncrement();
            // 如果 c + 1 也不能使得队列满，则唤醒池中的offer线程
            if (c + 1 < array.length) {
                tailWaits.signal();
            }
            // poll线程可能在等待队列不为空
            // 前面添加已经完成，队列不为空，可以唤醒
        } finally {
            tailLock.unlock();
        }
        // 如果c等于0的话，相当于这是第一个线程
        // 我们想要的就是只给第一个线程锁，其余的都不进行锁操作
        if (c == 0) {
            headLock.lock();
            try {
                headWaits.signal();
            } finally {
                headLock.unlock();
            }
        }
    }

    // @param timeout 表示等待的时间上限
    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        tailLock.lockInterruptibly();
        try {
            long nanos = TimeUnit.MILLISECONDS.toNanos(timeout);
            while (isFull()) {
                // 上次循环后没有剩余时间了
                // 且在while循环里队列是满的
                // 那么我放弃等待
                if (nanos <= 0) {
                    return false;
                }
                nanos = tailWaits.awaitNanos(nanos); // 返回等待的剩余时间
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            size.getAndIncrement();
            // poll线程可能在等待队列不为空
            // 前面添加已经完成，队列不为空，可以唤醒
        } finally {
            tailLock.unlock();
        }
        headLock.lock();
        try {
            headWaits.signal();
        } finally {
            headLock.unlock();
        }
        return true;
    }

    @Override
    public E poll() throws InterruptedException {
        headLock.lockInterruptibly();
        int c; // 取走前的元素个数
        E e;
        try {
            while (isEmpty()) {
                headWaits.await();
            }
            e = array[head];
            array[head] = null; // help GC垃圾回收
            if (++head == array.length) {
                head = 0;
            }
            c = size.getAndDecrement();
            // 当c > 1时，可以允许将线程池中的下一个线程唤醒
            if (c > 1) {
                headWaits.signal();
            }
        } finally {
            headLock.unlock();
        }

        // 当队列由满->不满时可以对offer方法进行唤醒
        if (c == array.length) {
            tailLock.lock();
            try {
                tailWaits.signal();
            } finally {
                tailLock.unlock();
            }
        }
        return e;
    }

    private boolean isEmpty() {
        return size.equals(0);
    }

    private boolean isFull() {
        return size.equals(array.length);
    }
}
