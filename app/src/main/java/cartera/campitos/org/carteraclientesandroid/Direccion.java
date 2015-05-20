package cartera.campitos.org.carteraclientesandroid;

/**
 * Created by campitos on 4/28/15.
 */
public class Direccion {
    private String estado;
    private String municipio;
    private String colonia;
    private Long cp;
    private String numero;

    @Override
    public String toString() {
        return "Direccion{" +
                "estado='" + estado + '\'' +
                ", municipio='" + municipio + '\'' +
                ", colonia='" + colonia + '\'' +
                ", cp=" + cp +
                ", numero='" + numero + '\'' +
                '}';
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Long getCp() {
        return cp;
    }

    public void setCp(Long cp) {
        this.cp = cp;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
