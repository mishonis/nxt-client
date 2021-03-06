package nxt.http;

import javax.servlet.http.HttpServletRequest;
import nxt.Asset;
import nxt.util.Convert;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

final class GetAsset
  extends HttpRequestHandler
{
  static final GetAsset instance = new GetAsset();
  
  public JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
  {
    String str = paramHttpServletRequest.getParameter("asset");
    if (str == null) {
      return JSONResponses.MISSING_ASSET;
    }
    Asset localAsset;
    try
    {
      localAsset = Asset.getAsset(Convert.parseUnsignedLong(str));
      if (localAsset == null) {
        return JSONResponses.UNKNOWN_ASSET;
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      return JSONResponses.INCORRECT_ASSET;
    }
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("account", Convert.convert(localAsset.getAccountId()));
    localJSONObject.put("name", localAsset.getName());
    if (localAsset.getDescription().length() > 0) {
      localJSONObject.put("description", localAsset.getDescription());
    }
    localJSONObject.put("quantity", Integer.valueOf(localAsset.getQuantity()));
    
    return localJSONObject;
  }
}