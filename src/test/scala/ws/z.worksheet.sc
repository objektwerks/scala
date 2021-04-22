/* Z ( VSCode-Metals ) Worksheet for Interviews */

import scala.annotation.tailrec

def fibonacci(n: Long): Long = {
  @tailrec
  def loop(n: Long, a: Long, b: Long): Long = n match {
    case 0 => a
    case _ => loop(n - 1, b, a + b)
  }
  loop(n, 0, 1)
}
fibonacci(9)

/*

// Given the following csv data, select a single price for every store, host,
// and UPC combination. The price selected should be based on the day of the
// week that the price was collected, where the days are ranked by priority.
//
// Day of week priority (high to low): Wednesday, Thursday, Friday, Saturday,
//                                     Tuesday, Monday, Sunday
//
// Assume csv data is very simple: 1 row corresponds to 1 line, all fields do 
// not contain ','

object Solution extends App {
  
  
  
  // Solution goes here
  
  
  
  def readAllCSVData() = """date,host,store_id,postal_code,upc,price
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,020525183675,12.88
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,300450481627,12.96
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,007379627104,28.77
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,305730169493,17.18
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,032700023058,4.27
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,030067399830,13.94
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,032700910327,5.77
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,681131178822,5.24
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,079656050813,8.94
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,858380002066,10.37
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,381371164509,10.97
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,070896160812,1.88
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,004127102944,2.74
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,037000520269,7.82
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,681131004879,5.87
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,079636259298,1.47
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,071190001795,10.98
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,818145018107,16.82
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,032700156510,4.86
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,035000688811,12.96
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,068113115134,5.74
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,045300362021,5.38
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,071758101127,1.14
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,002780010040,2.56
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,081083302595,9.44
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,017800179072,4.48
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,079400447777,5.58
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,022600019763,15.27
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,013562107995,6.92
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,068113118739,15.98
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,019800001094,13.44
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,079441001143,1.57
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,050000293292,0.48
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,079400525079,4.24
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,038100176691,14.83
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,307667858402,38.98
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,081174702518,
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,078742155463,3.0
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,010181032868,3.0
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,068113112485,2.74
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,603084260140,3.42
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,052336916722,3.57
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,190679000149,5.92
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,075371003066,6.48
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,075610004106,2.18
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,811068016229,4.87
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,603084543847,5.47
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,811068012818,4.87
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,802535554061,2.96
2018-09-30,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,019663506057,3.47
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,041167003916,4.24
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,001600032996,1.5
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,664255035295,67.82
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,022600901273,12.98
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,010181041709,5.68
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,657243175127,2.38
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,007940020174,4.24
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,005100001216,1.88
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,027917019451,9.88
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,063165670204,17.96
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,002500005677,3.64
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,043168981347,9.24
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,025700706687,4.78
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,012800517817,10.82
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,301937252013,14.98
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,681131074124,14.86
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,038000124938,10.98
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,054100936028,0.48
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,811068010517,4.87
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,060822008677,4.96
2018-09-30,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,078742141541,14.98
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,038100109897,9
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,071190006561,9.68
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,023100125770,3.47
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,047400662926,17.97
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,071190006035,8.48
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,722510926006,5.62
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,603084459308,5.47
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,605388186713,0.44
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,023100123097,1.26
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,042238300057,1.58
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,022400005225,4.92
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,013700449758,1.68
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,022796900937,5.74
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,810833023752,17.44
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,050000467150,7.38
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,024000163169,1.14
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,811068015024,4.87
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,004740011272,16.42
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,023100108322,0.7
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,021496236841,23.26
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,681131099646,4.34
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,681131068734,4.64
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,078742136431,1.24
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,681131099219,10.98
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,018000855582,3.54
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,040000497561,0.94
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,004460031192,9.34
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,050000570195,0.72
2018-09-30,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,815473015631,0.88
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,301937252013,17.58
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,681131074124,15.92
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,038000124938,3.20
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,054100936028,0.61
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,811068010517,4.51
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,060822008677,4.90
2018-10-01,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,078742141541,15.17
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,038100109897,9.01
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,071190006561,9.75
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,023100125770,3.47
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,047400662926,17.97
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,071190006035,8.48
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,722510926006,5.62
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,603084459308,5.47
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,605388186713,0.44
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,023100123097,1.26
2018-10-01,www.walmart.com,AArw2ziPS9SLWkdpeunlug==,76705,042238300057,1.58
2018-10-05,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,017800179072,2.48
2018-10-05,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,079400447777,4.58
2018-10-05,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,022600019763,18.27
2018-10-05,www.walmart.com,BEBOWwcHR5+FlLzK57Cvqw==,76903,013562107995,7.92
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,068113118739,16.98
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,019800001094,14.44
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,079441001143,2.57
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,050000293292,1.48
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,079400525079,5.24
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,038100176691,15.83
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,307667858402,3951
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,081174702518,
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,078742155463,3.50
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,010181032868,3.5
2018-10-05,www.walmart.com,Bd1DH8ssTWazXJ8Vy9KJCg==,73134,068113112485,2.86"""
}

*/