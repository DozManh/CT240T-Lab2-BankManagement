import model.BankAccount;
import model.SavingAccount;
import service.BankManager;
import exception.InvalidAmountException;
import exception.InsufficientBalanceException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankManager manager = new BankManager();
        Scanner scanner = new Scanner(System.in);

        try {
            manager.addAccount(new SavingAccount("TK001", "Nguyen Van A", 500000, 5.5));
            manager.addAccount(new SavingAccount("TK002", "Tran Thi B", 200000, 5.0));
        } catch (Exception e) {
            System.err.println("Lỗi khởi tạo: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n========== HỆ THỐNG QUẢN LÝ TÀI KHOẢN NGÂN HÀNG ==========");
            System.out.println("1. Thêm tài khoản tiết kiệm mới");
            System.out.println("2. Tra cứu thông tin tài khoản");
            System.out.println("3. Nạp tiền vào tài khoản");
            System.out.println("4. Rút tiền từ tài khoản");
            System.out.println("5. Chuyển tiền giữa 2 tài khoản");
            System.out.println("6. Xem danh sách & Tính tổng số dư toàn hệ thống");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng (0-6): ");

            String input = scanner.nextLine();
            System.out.println("----------------------------------------------------------");

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        System.out.print("Nhập số tài khoản: ");
                        String accNum = scanner.nextLine();
                        if (manager.findAccount(accNum) != null) {
                            System.out.println("Lỗi: Số tài khoản đã tồn tại!");
                            break;
                        }
                        System.out.print("Nhập tên chủ tài khoản: ");
                        String name = scanner.nextLine();
                        System.out.print("Nhập số dư ban đầu (>= 50,000 VNĐ): ");
                        double initBalance = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nhập lãi suất (%/năm): ");
                        double rate = Double.parseDouble(scanner.nextLine());

                        manager.addAccount(new SavingAccount(accNum, name, initBalance, rate));
                        System.out.println("-> Thêm tài khoản thành công!");
                        break;

                    case 2:
                        System.out.print("Nhập số tài khoản cần tìm: ");
                        BankAccount acc = manager.findAccount(scanner.nextLine());
                        System.out.println(acc != null ? acc : "-> Không tìm thấy tài khoản!");
                        break;

                    case 3:
                        System.out.print("Nhập số tài khoản nạp tiền: ");
                        BankAccount depAcc = manager.findAccount(scanner.nextLine());
                        if (depAcc == null) {
                            System.out.println("-> Không tìm thấy tài khoản!");
                            break;
                        }
                        System.out.print("Nhập số tiền nạp: ");
                        depAcc.deposit(Double.parseDouble(scanner.nextLine()));
                        System.out.println("-> Nạp tiền thành công! Số dư: " + String.format("%,.0f", depAcc.getBalance()) + " VNĐ");
                        break;

                    case 4:
                        System.out.print("Nhập số tài khoản rút tiền: ");
                        BankAccount withAcc = manager.findAccount(scanner.nextLine());
                        if (withAcc == null) {
                            System.out.println("-> Không tìm thấy tài khoản!");
                            break;
                        }
                        System.out.print("Nhập số tiền rút: ");
                        withAcc.withdraw(Double.parseDouble(scanner.nextLine()));
                        System.out.println("-> Rút tiền thành công! Số dư: " + String.format("%,.0f", withAcc.getBalance()) + " VNĐ");
                        break;

                    case 5:
                        System.out.print("Nhập số tài khoản người gửi: ");
                        String from = scanner.nextLine();
                        System.out.print("Nhập số tài khoản người nhận: ");
                        String to = scanner.nextLine();
                        System.out.print("Nhập số tiền chuyển: ");
                        double amount = Double.parseDouble(scanner.nextLine());

                        manager.transferMoney(from, to, amount);
                        System.out.println("-> Chuyển tiền thành công!");
                        break;

                    case 6:
                        System.out.println("--- DANH SÁCH TÀI KHOẢN ---");
                        for (BankAccount a : manager.getAllAccounts()) {
                            System.out.println(a);
                        }
                        double total = manager.calculateTotalBalance(manager.getAllAccounts());
                        System.out.println("----------------------------------------------------------");
                        System.out.println("TỔNG SỐ DƯ HỆ THỐNG: " + String.format("%,.0f", total) + " VNĐ");
                        break;

                    case 0:
                        running = false;
                        System.out.println("Đã thoát ứng dụng.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("[LỖI ĐỊNH DẠNG] Vui lòng nhập số hợp lệ!");
            } catch (InvalidAmountException e) {
                System.err.println("[LỖI SỐ TIỀN] " + e.getMessage());
            } catch (InsufficientBalanceException e) {
                System.err.println("[LỖI SỐ DƯ] " + e.getMessage());
            } catch (Exception e) {
                System.err.println("[LỖI HỆ THỐNG] " + e.getMessage());
            }
        }
        scanner.close();
    }
}