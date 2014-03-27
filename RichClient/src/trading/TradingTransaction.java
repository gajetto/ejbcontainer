package trading;

import java.io.Serializable;

/**
 *
 * @author Sohaila.Baset
 */
public class TradingTransaction implements Serializable{
    
    private TradingTransactionType type;
    private StockProduct product;
    private int qty;
    private String clientName;

    
    public TradingTransaction(TradingTransactionType type,StockProduct product,int qty,String clientName)
    {
        this.product=product;
        this.qty=qty;
        this.type=type;
        this.clientName=clientName;
    }
    
    
    /**
     * @return the type
     */
    public TradingTransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TradingTransactionType type) {
        this.type = type;
    }

    /**
     * @return the product
     */
    public StockProduct getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(StockProduct product) {
        this.product = product;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}


