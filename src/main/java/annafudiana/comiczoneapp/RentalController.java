package annafudiana.comiczoneapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class RentalController implements Initializable {
    public Label txtfBillGrandTotal;
    public TextField txtfIDrental;
    public TextField txtfIDCustomer;
    public TextField txtfCustomerName;
    public TextField txtfIDEmployee;
    public TextField txtfEmployeeName;
    public ComboBox<String> cbxBookCode;
    public TextField txtfBookName;
    public TextField txtfBookPrice;
    public DatePicker dpDateBorrow;
    public DatePicker dpDateReturn;
    public TableView<TableOrders> TabelOrderAll;
    public TableColumn<TableOrders, String> columnBookCode;
    public TableColumn<TableOrders, String> columnBookName;
    public TableColumn<TableOrders, Date> columnDateBorrow;
    public TableColumn<TableOrders, Date> columnDateReturn;
    public TableColumn<TableOrders, Integer> columnPriceList;
    public TextField txtfPayment;
    public TextField txtfChangePayment;

    // Error Notification
    public void showError(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText(null);
        error.setContentText(message);
        error.showAndWait();
    }

    // Warning Notification
    public void showWarning(String message) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Warning");
        warning.setHeaderText(null);
        warning.setContentText(message);
        warning.showAndWait();
    }

    // Succeed Notification
    private void showSucceed() {
        Alert succeed = new Alert(Alert.AlertType.INFORMATION);
        succeed.setTitle("Succeed");
        succeed.setHeaderText(null);
        succeed.setContentText("Transaksi Berhasil");
        succeed.showAndWait();
    }

    private final Connection conn = DatabaseConnection.getInstance().getConnection();

    public void btnNamaCustomerCheck(ActionEvent event) {
        // Get the customer ID from txtfIDCustomer
        String customerID = txtfIDCustomer.getText();

        // Check if the customer ID exists in the database
        String query = "SELECT Nama_customer FROM customer WHERE Id_customer = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // If the customer ID exists, set the Nama_customer value in txtfCustomerName
                String customerName = resultSet.getString("Nama_customer");
                txtfCustomerName.setText(customerName);
            } else if (txtfIDCustomer.getText().isBlank() || txtfIDCustomer.getText().isEmpty()) {
                showError("Isi ID Customer!!");
                txtfIDCustomer.requestFocus();
            }else {
                // If the customer ID does not exist, show an error message
                showError("ID '" + customerID + "' tidak ditemukan!!");
                txtfIDCustomer.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnNamaKaryawanCheck(ActionEvent event) {
        // Get the employee ID from txtfIDKaryawan
        String employeeID = txtfIDEmployee.getText();

        // Check if the employee ID exists in the database
        String query = "SELECT Nama_karyawan FROM karyawan WHERE Id_karyawan = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, employeeID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // If the employee ID exists, set the Nama_karyawan value in txtfEmployeeName
                String employeeName = resultSet.getString("Nama_karyawan");
                txtfEmployeeName.setText(employeeName);
            } else if (txtfIDEmployee.getText().isBlank() || txtfIDEmployee.getText().isEmpty()) {
                showError("Isi ID Karyawan!!");
                txtfIDEmployee.requestFocus();
            }else {
                // If the employee ID does not exist, show an error message
                showError("ID '" + employeeID + "' tidak ditemukan!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the ComboBox with the Id_buku values from the books table
        try {
            // Create a statement to execute the SQL query
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Id_buku, Nama_buku, Harga FROM books");

            // Loop through the results and add the Id_buku values to the ComboBox
            while (resultSet.next()) {
                String id = resultSet.getString("Id_buku");
                cbxBookCode.getItems().add(id);
            }

            // Close the result set and statement (do not close the connection)
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add a listener to the ComboBox to update the book name and price fields
        cbxBookCode.setOnAction(event -> {
            try {
                String selectedId = cbxBookCode.getValue();

                // Create a statement to execute the SQL query
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT Nama_buku, Harga FROM books WHERE Id_buku = '" + selectedId + "'");

                // Update the book name and price fields
                if (resultSet.next()) {
                    String bookName = resultSet.getString("Nama_buku");
                    int bookPrice = resultSet.getInt("Harga");

                    txtfBookName.setText(bookName);
                    txtfBookPrice.setText(Integer.toString(bookPrice));
                }

                // Close the result set and statement (do not close the connection)
                resultSet.close();
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();

    public void btnAddItemClick(ActionEvent event) {

        if (txtfIDrental.getText().isEmpty() || txtfIDCustomer.getText().isEmpty() || txtfIDEmployee.getText().isEmpty() ||
                cbxBookCode.getValue() == null || dpDateBorrow.getValue() == null || dpDateReturn.getValue() == null) {
            showError("Isi Semua Fields!!");
            return;
        }

        // Check if idSewa already exists in the sewa table
        String idSewa = txtfIDrental.getText().trim();
        try {
            String query = "SELECT COUNT(*) FROM sewa WHERE Id_sewa = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idSewa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                showError("Id Rental '"+ idSewa +"' sudah digunakan.");
                return;
            }
        } catch (SQLException ex) {
            showError("Error checking for existing Id Sewa: " + ex.getMessage());
            return;
        }

        columnBookCode.setCellValueFactory(new PropertyValueFactory<>("bookCode"));
        columnBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnDateBorrow.setCellValueFactory(new PropertyValueFactory<>("dateBorrow"));
        columnDateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));
        columnPriceList.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

        String bookCode = cbxBookCode.getValue();
        String bookName = txtfBookName.getText();
        Date dateBorrow = Date.valueOf(dpDateBorrow.getValue());
        Date dateReturn = Date.valueOf(dpDateReturn.getValue());
        int bookPrice = Integer.parseInt(txtfBookPrice.getText());

        // Check if the bookCode already exists in the table
        for (TableOrders item : ordersList) {
            if (item.getBookCode().equals(bookCode)) {
                showError("Tidak boleh menyewa buku yang sama!");
                // delete value
                cbxBookCode.setValue(null);
                txtfBookName.clear();
                txtfBookPrice.clear();
                // focus
                cbxBookCode.requestFocus();
                return;
            }
        }

        // Add a new TableOrders item to the list
        TableOrders newOrder = new TableOrders(bookCode, bookName, dateBorrow, dateReturn, bookPrice);
        ordersList.add(newOrder);

        // Set the ordersList as the data source for the TableView
        TabelOrderAll.setItems(null);
        this.TabelOrderAll.setItems(ordersList);
        this.TabelOrderAll.refresh();

        //disable date
        dpDateBorrow.setDisable(true);
        dpDateReturn.setDisable(true);

        // Clear the input book fields
        cbxBookCode.setValue(null);
        txtfBookName.clear();
        txtfBookPrice.clear();

        //disable pink fields
        txtfIDrental.setDisable(true);
        txtfIDCustomer.setDisable(true);
        txtfIDEmployee.setDisable(true);
        txtfCustomerName.setDisable(true);
        txtfEmployeeName.setDisable(true);

        // total the bills appear
        int grandTotal = 0;
        for (TableOrders item : ordersList) {
            grandTotal += item.getPrice();
        }
        txtfBillGrandTotal.setText(String.valueOf(grandTotal));
    }

    public void btnDeleteItemClick(ActionEvent event) {
        // Get the selected item from the TableView
        TableOrders selectedItem = TabelOrderAll.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            // Clear the input fields
            cbxBookCode.setValue(null);
            txtfBookName.clear();
            txtfBookPrice.clear();
        }

        // Remove the selected item from the ordersList
        ordersList.remove(selectedItem);

        // Refresh the TableView to reflect the changes
        TabelOrderAll.refresh();

        // Clear the input fields
        cbxBookCode.setValue(null);
        txtfBookName.clear();
        txtfBookPrice.clear();

        // Disable pink fields if the ordersList is empty
        if (ordersList.isEmpty()) {
            dpDateBorrow.setDisable(false);
            dpDateReturn.setDisable(false);
            txtfIDrental.setDisable(false);
            txtfIDCustomer.setDisable(false);
            txtfIDEmployee.setDisable(false);
            txtfCustomerName.setDisable(false);
            txtfEmployeeName.setDisable(false);
            // remove date
            dpDateBorrow.setValue(null);
            dpDateReturn.setValue(null);
        }

        // Recalculate the total bill
        int grandTotal = ordersList.stream().mapToInt(TableOrders::getPrice).sum();
        txtfBillGrandTotal.setText(String.valueOf(grandTotal));
    }

    public void btnSaveRecordClick(ActionEvent event) {
        try {
            if (txtfPayment.getText().isEmpty()) {
                showError("Pembayaran belum diisi!");
            } else {
                // Check if the input on payment is number
                try {
                    int Bayar = Integer.parseInt(txtfPayment.getText());
                    int Bill = Integer.parseInt(txtfBillGrandTotal.getText());

                    if (Bayar == Bill) {
                        // zero change
                        int change0 = 0;
                        txtfChangePayment.setDisable(false);
                        txtfChangePayment.setText(String.valueOf(change0));
                        txtfChangePayment.requestFocus();

                        SaveIntoDatabae();

                        showSucceed();

                        ClearEnable();

                     } else if (Bayar > Bill) {
                        // Show change
                        int change = Bayar - Bill;
                        txtfChangePayment.setDisable(false);
                        txtfChangePayment.setText(String.valueOf(change));
                        txtfChangePayment.requestFocus();

                        showWarning("Kembalian pelanggan Rp. " + change);

                        SaveIntoDatabae();

                        showSucceed();

                        ClearEnable();
                    }
                    else {
                        txtfPayment.requestFocus();
                        showError("Kurang bayar!");
                    }

                } catch (NumberFormatException e) {
                    showError("Harus input Angka!");
                    txtfPayment.requestFocus();
                }
            }
        } catch (Exception e) {
            System.out.println("Error penyimpanan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void SaveIntoDatabae() {
        // Insert the data into the database
        try {
            // Prepare statement to insert data into sewa table
            String insertSewaSql = "INSERT INTO sewa (Id_sewa, Id_customer, Id_karyawan, Tanggal_pinjam, Tanggal_kembali, Total_harga, Bayar, Kembalian) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertSewaStmt = conn.prepareStatement(insertSewaSql);

            // Set values for sewa table parameters
            insertSewaStmt.setString(1, txtfIDrental.getText());
            insertSewaStmt.setString(2, txtfIDCustomer.getText());
            insertSewaStmt.setString(3, txtfIDEmployee.getText());
            insertSewaStmt.setDate(4, Date.valueOf(dpDateBorrow.getValue()));
            insertSewaStmt.setDate(5, Date.valueOf(dpDateReturn.getValue()));
            insertSewaStmt.setInt(6, Integer.parseInt(txtfBillGrandTotal.getText()));
            insertSewaStmt.setInt(7, Integer.parseInt(txtfPayment.getText()));
            insertSewaStmt.setInt(8, Integer.parseInt(txtfChangePayment.getText()));

            // Execute sewa table insert statement
            insertSewaStmt.executeUpdate();

            // Close the SQL statement
            insertSewaStmt.close();

            System.out.println("Data Sewa berhasil disimpan, Id_sewa: " + txtfIDrental.getText());
        } catch (SQLException e) {
            // Handle any exceptions that occur
            showError("Data Sewa gagal disimpan!");
            System.out.println("ERROR Sewa di: " + e.getMessage());
            e.printStackTrace();
        }

        // Generate Id_sewa_detail
        int idSewaDetail = 1;
        try {
            String selectMaxIdSql = "SELECT MAX(Id_sewa_detail) FROM sewa_detail";
            Statement selectMaxIdStmt = conn.createStatement();
            ResultSet maxIdResult = selectMaxIdStmt.executeQuery(selectMaxIdSql);
            if (maxIdResult.next()) {
                idSewaDetail = maxIdResult.getInt(1) + 1;
            }
            selectMaxIdStmt.close();
        } catch (SQLException e) {
            showError("Gagal mendapatkan Id_sewa_detail!");
            System.out.println("ERROR Id_sewa_detail: " + e.getMessage());
            e.printStackTrace();
        }

        // Insert data into sewa_detail table
        try {
            // Loop through the ordersList and insert each bookCode into the sewa_detail table
            for (TableOrders order : ordersList) {
                String bookCode = order.getBookCode();

                String insertSewaDetailSql = "INSERT INTO sewa_detail ( Id_sewa_detail, Id_sewa, Id_buku) VALUES (?, ?, ?)";
                PreparedStatement insertSewaDetailStmt = conn.prepareStatement(insertSewaDetailSql);
                insertSewaDetailStmt.setInt(1, idSewaDetail);
                insertSewaDetailStmt.setString(2, txtfIDrental.getText());
                insertSewaDetailStmt.setString(3, bookCode);

                insertSewaDetailStmt.executeUpdate();

                System.out.println("Data Sewa Detail berhasil disimpan, Id_sewa_detail: " + idSewaDetail);

                //increment id
                idSewaDetail++;

                insertSewaDetailStmt.close();
            }
        } catch (SQLException e) {
            showError("Data Sewa Detail gagal disimpan!");
            System.out.println("ERROR Sewa Detail: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void ClearEnable() {
        // Clear the form
        txtfIDrental.clear();
        txtfIDCustomer.clear();
        txtfCustomerName.clear();
        txtfIDEmployee.clear();
        txtfEmployeeName.clear();
        dpDateBorrow.setValue(null);
        dpDateReturn.setValue(null);
        TabelOrderAll.getItems().clear();
        txtfPayment.clear();
        txtfChangePayment.clear();
        txtfBillGrandTotal.setText("");

        // enable
        txtfIDrental.setDisable(false);
        txtfIDCustomer.setDisable(false);
        txtfCustomerName.setDisable(false);
        txtfIDEmployee.setDisable(false);
        txtfEmployeeName.setDisable(false);
        dpDateBorrow.setDisable(false);
        dpDateReturn.setDisable(false);
    }

    public void btnExitClick(ActionEvent event) {
        // Get a reference to the button's stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Close the stage
        stage.close();
    }
}
