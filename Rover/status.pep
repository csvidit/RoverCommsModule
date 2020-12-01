	br main
hpPtr:	.addrss heap

; Status Message Exapnaded Struct
SM_size:	.equate 32

; Time is big-endian
SM_time:	.equate 0
SM_time2:	.equate 2
SM_time3:	.equate 4


SM_light:	.equate 6
SM_long:	.equate 8
SM_lat:	.equate 10
SM_wind:	.equate 12
SM_elev:	.equate 14
SM_itemp:	.equate 16
SM_atemp:	.equate 18
SM_charg:	.equate 20

; Hours of Operation is big-endian
SM_hour1:	.equate 22
SM_hour2:	.equate 24

SM_indic:	.equate 26
SM_code:	.equate 28
SM_alert:	.equate 30

; parse_status_message(*buf)
pm_buf:	.equate 2

parse_SM:	ldwx 0,i
	
pm_for:	cpwx SM_size,i
	brgt pm_end
	
	deci pm_buf,sfx
	addx 2,i
	br pm_for
	
pm_end:	ret

; malloc()
; Precondition: A contains a number of bytes
; Postcondition: X contains a pointer to bytes
malloc:	ldwx hpPtr,d
	adda hpPtr,d
	stwa hpPtr,d
	ret

; main()
buf_p:	.equate 0	; Pointer to the message buffer

main:	subsp 2,i	; push buf
	
	ldwa SM_size,i
	call malloc
	stwx buf_p,s
	
	subsp 2,i	; push pm_buf
	stwx 0,s
	call parse_SM
	addsp 2,i	; pop pm_buf
	
	stop

heap:	.byte 0
.end

