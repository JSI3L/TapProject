/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tap_u3tv.classes;

import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author angel
 */
public class JSONManager {

    public static void exportToPDF() {
        try {
            ArrayList<Theme> posts = DBManager.readThemes();
            Document document = new Document();

            // Create a PdfWriter instance to write the document to a file
            PdfWriter.getInstance(document, new FileOutputStream("posts.pdf"));

            float[] columnsWidths = {6.6f, 3.3f};
            PdfPTable table = new PdfPTable(columnsWidths);
            table.setWidthPercentage(90f);

            Paragraph header = new Paragraph("Post");
            Paragraph header2 = new Paragraph("Comments");
            
            header.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 24));
            header2.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 24));
            
            PdfPCell cell = new PdfPCell(header);
            PdfPCell cell2 = new PdfPCell(header2);
            
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    
            table.addCell(cell);
            table.addCell(cell2);

            // Open the document
            document.open();

            // Add each item in the ArrayList as a paragraph to the document
            posts.stream().forEach(post -> {
                try {
                    table.addCell(post.getContent());
                    table.addCell(String.valueOf(DBManager.countPostComments(post.getId())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            document.add(table);
            // Close the document
            document.close();
            
            JOptionPane.showMessageDialog(null, "PDF exported successfullly :D");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONManager() {
    }

    public static void setRecentUsers(User last) {
        try {
            ArrayList<User> users = new ArrayList<>();

            users.add(getRecentUsers().get(1));
            users.add(last);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("recent_users.json", false));
            String json = new Gson().toJson(users);
            bufferedWriter.write(json);

            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getRecentUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("recent_users.json"));
            String data = "";
            String input = null;
            while ((input = bufferedReader.readLine()) != null) {
                data += input;
            }
            bufferedReader.close();

            // Transformar el String contenido en arreglo de json
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(data);

            for (int i = 0; i < jsonArray.size(); i++) {
                Gson gson = new Gson();
                User user = gson.fromJson(jsonArray.get(i).toString(), User.class);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
