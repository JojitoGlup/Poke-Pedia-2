
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InfoPokemon extends javax.swing.JFrame {

    public static final Logger LOGGER = ConfiguracionLogger.getLogger();
    private String nameP;
    private String descAtaque;
    private String descHabilidad;

    public InfoPokemon(String nombre) {
        initComponents();
        ocultar();
        String imginfo = "src\\main\\java\\buscando.png";
        String imgurl = "src\\main\\java\\atras.png";
        ImageIcon img = new ImageIcon(imgurl);
        ImageIcon bscr = new ImageIcon(imginfo);
        btnInfo1.setIcon(bscr);
        btnInfo2.setIcon(bscr);
        btnRegresar.setIcon(img);
        nameP = nombre;
        String url = PokemonTipos.getPokemonUrl();
        new Thread(new PokemonApiHandler(nameP, url)).start();

    }
    private void ocultar(){
        lbltexto.setVisible(false);
        lbltexto1.setVisible(false);
        lbltexto2.setVisible(false);
        lbltexto3.setVisible(false);
        lbldsc.setVisible(false);
        lbldsc1.setVisible(false);
        btnInfo1.setVisible(false);
        btnInfo2.setVisible(false);
        btnRegresar.setVisible(false);
        panDescripcion.setVisible(false);
    }
    private void mostrar(){
        lbltexto.setVisible(true);
        lbltexto1.setVisible(true);
        lbltexto2.setVisible(true);
        lbltexto3.setVisible(true);
        lbldsc.setVisible(true);
        lbldsc1.setVisible(true);
        btnInfo1.setVisible(true);
        btnInfo2.setVisible(true);
        btnRegresar.setVisible(true);
        panDescripcion.setVisible(true);
    }

    private class PokemonApiHandler implements Runnable {

        private String pokemon;
        private String pokemonurl;

        public PokemonApiHandler(String name, String url) {
            pokemon = name;
            pokemonurl = url;
        }

        @Override
        public void run() {
            PokemonNombre pokemonName = new PokemonNombre(pokemon, pokemonurl);
            ArrayList<String> pokemon = pokemonName.getPokemon();

            SwingUtilities.invokeLater(() -> {
                lbNombre.setText("" + pokemon.get(0));
                lblDetalles.setText("N: "+pokemon.get(8)+" "+
                        "Peso: "+pokemon.get(9)+" "+
                                "Altura: "+ pokemon.get(10));
                lblDetalles.setHorizontalAlignment(SwingConstants.CENTER);
                try {
                    String imageUrl = pokemon.get(1);
                    URL url = new URL(imageUrl);
                    BufferedImage image = ImageIO.read(url);
                    ImageIcon icon = new ImageIcon(image);
                    lbilustracion.setIcon(icon);
                } catch (IOException e) {
                    LOGGER.severe("Error al cargar la imagen: " + e.getMessage());
                }
                lblTipo.setText("- "+pokemon.get(2));
                lblDebilidad.setText("- "+pokemon.get(7));
                lblAtaque.setText("- "+pokemon.get(5));
                lblHabilidad.setText("- "+pokemon.get(3));
                
                descAtaque = pokemon.get(6);
                descHabilidad = pokemon.get(4);
                infoPokedex.setText("<html>"+pokemon.get(11)+"</html>");
                mostrar();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    LOGGER.severe("Error al correr el thread: " + e.getMessage());
                }
            });

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbilustracion = new javax.swing.JLabel();
        lbltexto = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lbltexto1 = new javax.swing.JLabel();
        lblDebilidad = new javax.swing.JLabel();
        lbltexto2 = new javax.swing.JLabel();
        lbltexto3 = new javax.swing.JLabel();
        lblAtaque = new javax.swing.JLabel();
        lblHabilidad = new javax.swing.JLabel();
        btnInfo1 = new javax.swing.JButton();
        btnInfo2 = new javax.swing.JButton();
        lbldsc = new javax.swing.JLabel();
        lbldsc1 = new javax.swing.JLabel();
        lblDetalles = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        panDescripcion = new javax.swing.JPanel();
        infoPokedex = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(lbilustracion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbilustracion, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
        );

        lbltexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltexto.setText("Ataque:");

        lbNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTipo.setBackground(new java.awt.Color(255, 255, 255));
        lblTipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbltexto1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltexto1.setText("Debilidad:");

        lblDebilidad.setBackground(new java.awt.Color(255, 255, 255));
        lblDebilidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbltexto2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltexto2.setText("Tipo:");

        lbltexto3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltexto3.setText("Habilidad:");

        lblAtaque.setBackground(new java.awt.Color(255, 255, 255));
        lblAtaque.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblHabilidad.setBackground(new java.awt.Color(255, 255, 255));
        lblHabilidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnInfo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfo1ActionPerformed(evt);
            }
        });

        btnInfo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfo2ActionPerformed(evt);
            }
        });

        lbldsc.setBackground(new java.awt.Color(255, 255, 255));
        lbldsc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbldsc.setText("Ver descripción:");

        lbldsc1.setBackground(new java.awt.Color(255, 255, 255));
        lbldsc1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbldsc1.setText("Ver descripción:");

        lblDetalles.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        panDescripcion.setBackground(new java.awt.Color(204, 204, 255));

        infoPokedex.setBackground(new java.awt.Color(255, 255, 255));
        infoPokedex.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        javax.swing.GroupLayout panDescripcionLayout = new javax.swing.GroupLayout(panDescripcion);
        panDescripcion.setLayout(panDescripcionLayout);
        panDescripcionLayout.setHorizontalGroup(
            panDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPokedex, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
        );
        panDescripcionLayout.setVerticalGroup(
            panDescripcionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPokedex, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                            .addComponent(lblAtaque, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(lbltexto2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblDebilidad, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                        .addComponent(lbltexto1)
                                        .addComponent(lblHabilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbltexto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(lbldsc)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(34, 34, 34)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbltexto3)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lbldsc1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(lblDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(24, 24, 24))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(lbNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbltexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltexto2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbltexto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDebilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbltexto3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAtaque, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHabilidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldsc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldsc1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        this.dispose();
        PokemonApp pkmnApp = new PokemonApp();
        pkmnApp.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnInfo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfo1ActionPerformed
        String texto = "<html><body style='width: 300px;'>" + descAtaque + "</body></html>";
        JOptionPane.showMessageDialog(null, texto,
                "INFO", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnInfo1ActionPerformed

    private void btnInfo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfo2ActionPerformed
        String texto = "<html><body style='width: 300px;'>" + descHabilidad + "</body></html>";
        JOptionPane.showMessageDialog(null, texto,
                "INFO", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnInfo2ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInfo1;
    private javax.swing.JButton btnInfo2;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel infoPokedex;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbilustracion;
    private javax.swing.JLabel lblAtaque;
    private javax.swing.JLabel lblDebilidad;
    private javax.swing.JLabel lblDetalles;
    private javax.swing.JLabel lblHabilidad;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lbldsc;
    private javax.swing.JLabel lbldsc1;
    private javax.swing.JLabel lbltexto;
    private javax.swing.JLabel lbltexto1;
    private javax.swing.JLabel lbltexto2;
    private javax.swing.JLabel lbltexto3;
    private javax.swing.JPanel panDescripcion;
    // End of variables declaration//GEN-END:variables
}
