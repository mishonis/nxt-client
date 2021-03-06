package nxt.http;

import javax.servlet.http.HttpServletRequest;
import nxt.Block;
import nxt.Blockchain;
import nxt.Transaction;
import nxt.util.Convert;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

final class GetTransactionBytes
  extends HttpRequestHandler
{
  static final GetTransactionBytes instance = new GetTransactionBytes();
  
  public JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
  {
    String str = paramHttpServletRequest.getParameter("transaction");
    if (str == null) {
      return JSONResponses.MISSING_TRANSACTION;
    }
    Long localLong;
    Transaction localTransaction;
    try
    {
      localLong = Convert.parseUnsignedLong(str);
      localTransaction = Blockchain.getTransaction(localLong);
    }
    catch (RuntimeException localRuntimeException)
    {
      return JSONResponses.INCORRECT_TRANSACTION;
    }
    JSONObject localJSONObject = new JSONObject();
    if (localTransaction == null)
    {
      localTransaction = Blockchain.getUnconfirmedTransaction(localLong);
      if (localTransaction == null) {
        return JSONResponses.UNKNOWN_TRANSACTION;
      }
      localJSONObject.put("bytes", Convert.convert(localTransaction.getBytes()));
    }
    else
    {
      localJSONObject.put("bytes", Convert.convert(localTransaction.getBytes()));
      Block localBlock = localTransaction.getBlock();
      localJSONObject.put("confirmations", Integer.valueOf(Blockchain.getLastBlock().getHeight() - localBlock.getHeight() + 1));
    }
    return localJSONObject;
  }
}