	br main
hpPtr:	.addrss heap

MSK_4GSB:	.equate 0xf000
MSK_12LB:	.equate 0x0fff

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

; Read the contents of one of the test files into an uncompressed struct.
; parse_status_message(*msg)
pm_msg:	.equate 2

parse_SM:	ldwx 0,i
	
pm_for:	cpwx SM_size,i
	brge pm_end
	
	deci pm_msg,sfx
	
	addx 2,i
	br pm_for
	
pm_end:	ret

; Status Message Compressed "Struct"
; Hexo can't print out an odd number of bytes, so we allocate 24 even though
;   we only need 23. The last byte is zeroed during conversion.
MSG_size:	.equate 24

MSG_latw:	.equate 10	; Beginning of the 3-byte chunk for latitude/wind speed
MSG_wnds:	.equate 12

MSG_elit:	.equate 13
MSG_itps:	.equate 15

MSG_subi:	.equate 16

MSG_hrs1:	.equate 18

MSG_atmp:	.equate 20
MSG_chrg:	.equate 22

; print_compressed(*buf)
pc_buf:	.equate 2

print_CM:	ldwx 0,i
	
pc_for:	cpwx MSG_size,i
	brge pc_end
	
	hexo pc_buf,sfx
	
	addx 2,i
	br pc_for
	
pc_end:	ret

; Take a pointer to an uncompressed status message and return a buffer of bytes
;   for the compressed message.
; compress_status_message(*msg) -> *buf
cm_buf:	.equate 4	; retval
cm_msg:	.equate 6

cm_wrkv:	.equate 0

press_SM:	subsp 2,i	; Push cm_wrkv
	
	ldwa MSG_size,i
	call malloc
	stwx cm_buf,s
	
	ldwx 0,i
cm_for:	cpwx 10,i	; Loop through the first 10 bytes of the message and copy verbatim
	brge cm_lat
	
	ldwa cm_msg,sfx
	stwa cm_buf,sfx
	
	addx 2,i
	br cm_for
	
	; Latitude and Wind Speed
cm_lat:	ldwa 0,i
	movaflg	; Need to make sure carry is zero because rol rotates through C
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
	ldba cm_msg,sfx	; Load *byte* so as to grab only the most significant bit
	
	ora cm_wrkv,s	; Combine msb of itemp with elevation; store
	ldwx MSG_elit,i
	stwa cm_buf,sfx
	
	ldwx SM_itemp,i	; Store the lsb's of internal temp
	ldwa cm_msg,sfx
	ldwx MSG_itps,i
	stba cm_buf,sfx
	
	; Subsystem Indicator/Codes
	ldwa 0,i
	movaflg
	ldwx SM_indic,i
	ldwa cm_msg,sfx
	rola
	rola
	rola
	rola
	rola
	rola
	
	stwa cm_wrkv,s
	
	ldwx SM_code,i
	ldwa cm_msg,sfx
	
	ora cm_wrkv,s	; Combine msb of indic with the code; store
	ldwx MSG_subi,i
	stwa cm_buf,sfx
	
	; Remaining fields: Hours, atemp, charge, alert
	ldwa 0,i
	movaflg
	ldwx SM_hour1,i
	ldwa cm_msg,sfx
	call rora5
	anda MSK_4GSB,i	; Take only the 4 greatest significant bits
	
	stwa cm_wrkv,s
	
	ldwa 0,i
	movaflg
	ldwx SM_hour2,i
	ldwa cm_msg,sfx
	call rora4
	anda MSK_12LB,i
	
	ora cm_wrkv,s
	
	ldwx MSG_hrs1,i
	stwa cm_buf,sfx	; Store GSB word of hours of operation
	
	ldwa 0,i	; 4 least-significant bits of hours of operation
	movaflg
	ldwx SM_hour2,i
	ldwa cm_msg,sfx
	call rora5
	anda MSK_4GSB,i
	
	stwa cm_wrkv,s
	
	ldwa 0,i	; Ambient temp shifts left two bits
	movaflg
	ldwx SM_atemp,i
	ldwa cm_msg,sfx
	rola
	rola
	
	ora cm_wrkv,s
	stwa cm_wrkv,s	; Store with ambient temp
	
	ldwa 0,i	; Alert bit
	movaflg
	ldwx SM_alert,i
	ldwa cm_msg,sfx
	rola
	
	ora cm_wrkv,s
	stwa cm_wrkv,s
	
	ldwa 0,i	; Charge level most significant bit
	ldwx SM_charg,i
	ldba cm_msg,sfx
	
	ora cm_wrkv,s
	ldwx MSG_atmp,i
	stwa cm_buf,sfx
	
	ldwa 0,i	; zero out the entire word; See struct comment
	ldwx MSG_chrg,i
	stwa cm_buf,sfx
	
	ldwx SM_charg,i	; Last byte of charge
	ldwa cm_msg,sfx
	
	ldwx MSG_chrg,i
	stba cm_buf,sfx
	
cm_end:	addsp 2,i
	ret

; malloc()
; Precondition: A contains a number of bytes
; Postcondition: X contains a pointer to bytes
malloc:	ldwx hpPtr,d
	adda hpPtr,d
	stwa hpPtr,d
	ret

; Rotate A right 4 times (DOES NOT CLEAR C)
; rora4()
rora4:	rora
	rora
	rora
	rora
	ret

; Rotate A right 5 times (DOES NOT CLEAR C)
; rora5()
rora5:	rora
	rora
	rora
	rora
	rora
	ret

; main()
buf_p:	.equate 0	; Pointer to the uncompressed message buffer
out_p:	.equate 2	; Pointer to the compressed message buffer

main:	subsp 4,i	; push out_p, buf_p
	
	ldwa SM_size,i
	call malloc
	stwx buf_p,s
	
	subsp 2,i	; push pm_buf
	stwx 0,s
	call parse_SM
	addsp 2,i	; pop pm_buf
	
	ldwx buf_p,s
	
	subsp 4,i	; push cm_buf, cm_msg
	stwx 2,s	; store X to cm_msg
	call press_SM
	ldwx 0,s	; load X from cm_buf
	addsp 4,i	; pop cm_msg, cm_buf
	
	stwx out_p,s
	
	subsp 2,i	; push pc_buf
	stwx 0,s
	call print_CM
	addsp 2,i	; pop pc_buf
	
	addsp 4,i	; pop buf_p, out_p
	stop

heap:	.byte 0
.end

