import resthuobi
import restbina
import restokex
import restzb
import  time

if __name__=="__main__":
    while True:
        restzb.getData()
        restokex.getData()
        restbina.getData()
        resthuobi.getData()
        time.sleep(30)
