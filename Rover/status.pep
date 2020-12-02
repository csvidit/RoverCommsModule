	br main
hpPtr:	.addrss heap

; Status Message Expanded Struct
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

; Status Message Compressed "Struct"
MSG_size:	.equate 23

MSG_latw:	.equate 10	; Beginning of the 3-byte chunk for latitude/wind speed
MSG_wnds:	.equate 12

MSG_elit:	.equate 13
MSG_itps:	.equate 15

MSG_subi:	.equate 16


; Read the contents of one of the test files into an uncompressed struct.
; parse_status_message(*msg)
pm_msg:	.equate 2

parse_SM:	ldwx 0,i
	
pm_for:	cpwx SM_size,i
	brgt pm_end
	
	deci pm_msg,sfx
	addx 2,i
	br pm_for
	
pm_end:	ret

; Take a pointer to an uncompressed status message and return a buffer of bytes
;   for the compressed message.
; compress_status_message(*msg) -> *buf
cm_msg:	.equate 6
cm_buf:	.equate 4	; retval

cm_wrkv:	.equate 0

press_SM:	subsp 2,i	; Push cm_wrkv
	
	ldwa MSG_size,i
	call malloc
	stwx cm_buf,s
	
	ldwx 0,i
cm_for:	cpwx 10,i	; Loop through the first 10 bytes of the message and copy verbatim
	brgt cm_lat
	
	ldwa cm_msg,sfx
	stwa cm_buf,sfx
	
	addx 2,i
	br cm_for
	
	; Latitude and Wind Speed
cm_lat:	ldwa 0,i
	movaflg	; Need to make sure carry is zero
	ldwx SM_lat,i
	ldwa cm_msg,sfx
	rola
	
	stwa cm_wrkv,s
	
	ldwa 0,i
	ldwx SM_wind,i
	ldba cm_msg,sfx	; Load *byte* so as to grab only the most significant bit
	
	ora cm_wrkv,s	; Combine msb of wind speed to lat; store
	ldwx MSG_latw,i
	stwa cm_buf,sfx
	
	ldwx SM_wind,i	; Store the least-significant bits of wind speed
	ldwa cm_msg,sfx
	ldwx MSG_wnds,i
	stba cm_buf,sfx
	
	; Elevation/Internal Temp
	ldwa 0,i
	movaflg
	ldwx SM_elev,i
	ldwa cm_msg,sfx
	rola
	
	stwa cm_wrkv,s
	
	ldwa 0,i
	ldwx SM_itemp,i
	ldwa cm_msg,sfx
	
	ora cm_wrkv,s	; Combine msb of itemp with elevation; store
	ldwx MSG_elit,i
	stwa cm_buf,sfx
	
	ldwx SM_itemp,i	; Store the lsb's of internal temp
	ldwa cm_msg,sfx
	ldwx MSG_itps,i
	stba cm_buf,sfx
	
	; Subsystem Indicator/Codes
	
cm_end:	ret

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

