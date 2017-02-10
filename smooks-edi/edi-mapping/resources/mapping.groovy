import com.wjelogistics.edi.mapping.iftmbf.HeadRecord

def headRecord = new HeadRecord()
headRecord.setSenderCode(src.getHeadRecord().getSenderCode())
headRecord.setFileFunction("3")
dest.setHeadRecord(headRecord)

return dest