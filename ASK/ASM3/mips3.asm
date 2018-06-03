.data

.text
main:
	#get time
	li $v0 30
	syscall				#low in $a0, high in $a1
	
	#set seed
	li $v0 40			#takes $a1 as seed
	syscall
	
	#counter
	li $t0 10
	
	#load bitmasks
	li $t8 0x0000ffff
	li $t9 0xffff0000
	loop:
		beqz $t0 end
		subi $t0 $t0 1		#decrement counter
		
		li $v0 41		#generate random
		syscall
		move $t1 $a0
		
		and $a0 $t1 $t8		#cut off 16 bits and print
		li $v0 1
		syscall
		
		li $a0 '\n'		#next line
		li $v0 11
		syscall
		
		and $a0 $t1 $t9		#cut off 16 bits
		srl $a0 $a0 16		#shift bits to the right
		li $v0 1
		syscall
	
		li $a0 '\n'		#next line
		li $v0 11
		syscall
		
		j loop
	
	end:
	