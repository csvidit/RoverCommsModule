	br main
hpPtr:	.addrss heap

; asbyte(byte) -> byte
ab_char:	.equate 4
ab_ret:	.equate 2
asbyte:	stro call_ab,d
	hexo ab_char,s
	stro parmsep,d
	
	ldwa ab_char,s
	
	; Convert char to uppercase
	cpwa 'Z',i	; Lowercase is higher than uppercase
	brle ab_dcod
	suba 0x20,i
	
ab_dcod:	cpwa '0',i
	brlt ab_err
	cpwa '9',i
	brle ab_dig
	
	cpwa 'A',i
	brlt ab_err
	cpwa 'F',i
	brle ab_let
	br ab_err
	
ab_dig:	suba 0x30,i
	br ab_end
	
ab_let:	suba 0x37,i
	br ab_end
	
ab_err:	stro ab_ermsg,d
	hexo ab_char,s
	stop
	
ab_end:	stwa ab_ret,s
	hexo ab_ret,s
	stro newline,d
	ret

; hexin(*buf, len)
hi_buf:	.equate 4
hi_len:	.equate 6

hi_byte:	.equate 0

hexin:	subsp 2,i
	ldwx 0,i
	
hi_for:	cpwx hi_len,s
	brgt hi_end
	
	ldwa 0,i
	ldba charIn,d	; Filter spaces/newlines
	cpwa ' ',i
	breq hi_for
	cpwa '\n',i
	breq hi_for
	
	subsp 4,i
	stwa 2,s
	call asbyte
	ldwa 0,i
	addsp 4,i
	
	movaflg	; clear carry bit (copies most significant bits, which are empty)
	ldwa -4,s	; Grab the return value of as_byte
	rola
	rola
	rola
	rola
	stwa hi_byte,s
	
hi_two:	ldwa 0,i
	ldba charIn,d	; Filter spaces/newlines
	cpwa ' ',i
	breq hi_two
	cpwa '\n',i
	breq hi_two
	
	subsp 4,i
	stwa 2,s
	call asbyte
	ldwa 0,i
	ldwa 0,s
	addsp 4,i
	
	ora hi_byte,s
	stba hi_buf,sfx
	
	addx 1,i
	br hi_for
	
hi_end:	addsp 2,i
	ret

; malloc()
; Precondition: A contains a number of bytes
; Postcondition: X contains a pointer to bytes
malloc:	ldwx hpPtr,d
	adda hpPtr,d
	stwa hpPtr,d
	ret

; main()
msglen:	.equate 21
buf:	.equate 0
	
main:	subsp 2,i
	
	ldwa msglen,i
	call malloc
	
	subsp 4,i
	stwx 0,s
	
	ldwa msglen,i
	stwa 2,s
	
	call hexin
	addsp 4,i
	
	addsp 2,i
	stop
	
ab_ermsg:	.ascii "Invalid hex digit: \x00"
call_ab:	.ascii "Call as_byte: \x00"
parmsep:	.ascii ", "
newline:	.ascii "\n\x00"
heap:	.block 1
.end

