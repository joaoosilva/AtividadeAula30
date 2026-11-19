package br.com.starlog.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexaoFactory {
    private static final String URL = "jdbc:sqlite:starlog.db";

    private ConexaoFactory() {
    }

    public static Connection obterConexao() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS produtos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome TEXT NOT NULL,
                            preco REAL NOT NULL,
                            estoque INTEGER NOT NULL
                        );
                        """);
            }
            return conn;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeException) {
                    e.addSuppressed(closeException);
                }
            }
            throw new RuntimeException("Erro ao obter conexão com o banco de dados: " + e.getMessage(), e);
        }
    }
}
