import resthuobi
import restbina
import restokex
import restzb
import  time
import multiprocessing

if __name__=="__main__":
    while True:
        p1=multiprocessing.Process(target=restzb.getData())
        p2=multiprocessing.Process(target=restokex.getData())
    #    p3=multiprocessing.Process(target=resthuobi.getData())
        #p4=multiprocessing.Process(target=restzb.getData())
        p1.start()
        p2.start()
     #   p3.start()
        p1.join()
        p2.join()
      #  p3.join()
    #while True:
    #    restzb.getData()
    #    restokex.getData()
       # restbina.getData()
       # resthuobi.getData()
#        time.sleep(10)
