package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class FileCopyTest {
    private static final String SOURCE_FILE = "src/test/resources/read.txt";
    private static final String DEST_FILE = "src/test/resources/write.txt";

    private FileCopy fileCopy;

    @BeforeEach
    void setUp() throws IOException {
        fileCopy = new FileCopy();

        // Tạo file nguồn với nội dung mẫu
        File source = new File(SOURCE_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(source))) {
            writer.write("Hello, World!");
            writer.newLine();
            writer.write("This is a test file.");
        }
    }

    @AfterEach
    void tearDown() {
        // Xóa các file test sau khi chạy test
        new File(SOURCE_FILE).delete();
        new File(DEST_FILE).delete();
    }

    @Test
    void testCopyFileSuccess() {
        // Thực hiện sao chép
        fileCopy.copyFile(SOURCE_FILE, DEST_FILE);

        // Kiểm tra tệp đích tồn tại
        File dest = new File(DEST_FILE);
        assertTrue(dest.exists(), "File đích không tồn tại sau khi sao chép.");

        // Kiểm tra nội dung tệp đích
        try (BufferedReader reader = new BufferedReader(new FileReader(dest))) {
            assertEquals("Hello, World!", reader.readLine());
            assertEquals("This is a test file.", reader.readLine());
        } catch (IOException e) {
            fail("Lỗi đọc file đích: " + e.getMessage());
        }
    }

    @Test
    void testCopyFileSourceNotExist() {
        // Gọi phương thức với tệp không tồn tại
        fileCopy.copyFile("src/test/resources/non_existing.txt", DEST_FILE);

        // Kiểm tra rằng file đích không được tạo
        assertFalse(new File(DEST_FILE).exists(), "File đích không nên tồn tại nếu tệp nguồn không tồn tại.");
    }

    @Test
    void testCopyFileWriteError() {
        // Tạo file đích và đặt quyền chỉ đọc
        File dest = new File(DEST_FILE);
        try {
            dest.createNewFile();
            dest.setReadOnly();

            // Gọi phương thức sao chép (nên xảy ra lỗi ghi)
            fileCopy.copyFile(SOURCE_FILE, DEST_FILE);
        } catch (IOException e) {
            fail("Lỗi khi thiết lập môi trường kiểm thử: " + e.getMessage());
        } finally {
            dest.setWritable(true); // Đặt lại quyền để có thể xóa file sau test
        }
    }
}
