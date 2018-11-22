package model;

/**
 * Modelo para guardar os gastos em tempo de execução. Atributos iguais aos da
 * tabela fornecida
 */
public class Gasto {

    private Integer idGasto;
    private String bugged_date;
    private String receipt_date;
    private Integer deputy_id;
    private String political_party;
    private String state_code;
    private String deputy_name;
    private String receipt_social_security_number;
    private String receipt_description;
    private String establishment_name;
    private Integer receipt_value;

    public Gasto(String bugged_date, String receipt_date, Integer deputy_id, String political_party, String state_code, String deputy_name, String receipt_social_security_number, String receipt_description, String establishment_name, Integer receipt_value) {
        this.bugged_date = bugged_date;
        this.receipt_date = receipt_date;
        this.deputy_id = deputy_id;
        this.political_party = political_party;
        this.state_code = state_code;
        this.deputy_name = deputy_name;
        this.receipt_social_security_number = receipt_social_security_number;
        this.receipt_description = receipt_description;
        this.establishment_name = establishment_name;
        this.receipt_value = receipt_value;
    }

    public Gasto() {
    }

    public String getBugged_date() {
        return bugged_date;
    }

    public void setBugged_date(String bugged_date) {
        this.bugged_date = bugged_date;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public Integer getDeputy_id() {
        return deputy_id;
    }

    public void setDeputy_id(Integer deputy_id) {
        this.deputy_id = deputy_id;
    }

    public String getPolitical_party() {
        return political_party;
    }

    public void setPolitical_party(String political_party) {
        this.political_party = political_party;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getDeputy_name() {
        return deputy_name;
    }

    public void setDeputy_name(String deputy_name) {
        this.deputy_name = deputy_name;
    }

    public String getReceipt_social_security_number() {
        return receipt_social_security_number;
    }

    public void setReceipt_social_security_number(String receipt_social_security_number) {
        this.receipt_social_security_number = receipt_social_security_number;
    }

    public String getReceipt_description() {
        return receipt_description;
    }

    public void setReceipt_description(String receipt_description) {
        this.receipt_description = receipt_description;
    }

    public String getEstablishment_name() {
        return establishment_name;
    }

    public void setEstablishment_name(String establishment_name) {
        this.establishment_name = establishment_name;
    }

    public Integer getReceipt_value() {
        return receipt_value;
    }

    public void setReceipt_value(Integer receipt_value) {
        this.receipt_value = receipt_value;
    }

    public Integer getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

}
